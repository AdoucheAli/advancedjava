package com.joshcummings.codeplay.concurrency.splitting;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Callable;

import com.joshcummings.codeplay.concurrency.BadIdentity;
import com.joshcummings.codeplay.concurrency.Identity;
import com.joshcummings.codeplay.concurrency.IdentityReader;
import com.joshcummings.codeplay.concurrency.MalformedIdentityRepository;
import com.joshcummings.codeplay.concurrency.single.CopyingInputStream;
import com.joshcummings.codeplay.concurrency.splitting.ScatterGatherer.Gatherer;
import com.joshcummings.codeplay.concurrency.splitting.ScatterGatherer.Scatterer;


public class MultiStrategyIdentityReader implements IdentityReader {
	protected final ScatterGatherer scatterGatherer;
	protected final IdentityReader primary;
	protected final List<IdentityReader> secondary;
	protected final MalformedIdentityRepository malformed;
	
	public MultiStrategyIdentityReader(List<IdentityReader> readers, ScatterGatherer scatterGatherer, MalformedIdentityRepository repository) {
		this.primary = readers.stream().findFirst().orElseThrow(IllegalArgumentException::new);
		this.secondary = readers.subList(1, readers.size());
		this.scatterGatherer = scatterGatherer;
		this.malformed = repository;
	}
	
	@Override
	public Identity read(InputStream is) {
		try ( CopyingInputStream cis = new CopyingInputStream(is); ) {
			Identity i = primary.read(cis);
			if ( i instanceof BadIdentity ) { 
				Identity result = scatterGatherer.go(new ReaderScatterer(cis, secondary), new IdentityGatherer());
				
				if ( result instanceof BadIdentity ) {
					malformed.addIdentity(cis.reread(), "Tried all identity serialization strategies and all failed");
				} else { 
					return result;
				}
			} else {
				return i;
			}
		} catch ( IOException e ) {
			throw new IllegalStateException("Something terrible happened with the re-read stream", e);
		}

		return read(is);
	}

	public static class ReaderScatterer implements Scatterer {
		private int index;
		private List<IdentityReader> readers;
		private CopyingInputStream cis;
		
		public ReaderScatterer(CopyingInputStream cis, List<IdentityReader> readers) {
			this.readers = readers;
			this.cis = cis;
		}

		@Override
		public boolean hasNext() {
			return index < readers.size();
		}

		@Override
		public Callable<Identity> next() {
			System.out.println(Thread.currentThread() + " says 'next' " + index);
			final int which = index++;
			return () -> readers.get(which).read(cis.reread());
		}
	};

	public static class IdentityGatherer implements Gatherer {
		private Identity result;

		@Override
		public boolean needsMore() {
			return result == null || result instanceof BadIdentity;
		}

		@Override
		public void gatherResult(Identity result) {
			System.out.println(Thread.currentThread() + " says 'gatherResult'");
			this.result = result;
		}

		@Override
		public Identity getFinalResult() {
			return result;
		}
	}
}