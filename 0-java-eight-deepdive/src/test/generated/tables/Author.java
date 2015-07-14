/**
 * This class is generated by jOOQ
 */
package test.generated.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;

import test.generated.Keys;
import test.generated.Library;
import test.generated.tables.records.AuthorRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.6.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Author extends TableImpl<AuthorRecord> {

	private static final long serialVersionUID = 870076471;

	/**
	 * The reference instance of <code>library.author</code>
	 */
	public static final Author AUTHOR = new Author();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<AuthorRecord> getRecordType() {
		return AuthorRecord.class;
	}

	/**
	 * The column <code>library.author.id</code>.
	 */
	public final TableField<AuthorRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>library.author.first_name</code>.
	 */
	public final TableField<AuthorRecord, String> FIRST_NAME = createField("first_name", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>library.author.last_name</code>.
	 */
	public final TableField<AuthorRecord, String> LAST_NAME = createField("last_name", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * Create a <code>library.author</code> table reference
	 */
	public Author() {
		this("author", null);
	}

	/**
	 * Create an aliased <code>library.author</code> table reference
	 */
	public Author(String alias) {
		this(alias, AUTHOR);
	}

	private Author(String alias, Table<AuthorRecord> aliased) {
		this(alias, aliased, null);
	}

	private Author(String alias, Table<AuthorRecord> aliased, Field<?>[] parameters) {
		super(alias, Library.LIBRARY, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<AuthorRecord> getPrimaryKey() {
		return Keys.KEY_AUTHOR_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<AuthorRecord>> getKeys() {
		return Arrays.<UniqueKey<AuthorRecord>>asList(Keys.KEY_AUTHOR_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Author as(String alias) {
		return new Author(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Author rename(String name) {
		return new Author(name, null);
	}
}
