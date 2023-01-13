package net.associal.bbooked.entity.searchPattern;

/**
 * Interface for all search patterns
 * Usually sql string is something like
 * select _columns_ from _tables_ _where_
 * each search pattern adds columns (getColumns), tables (getJoins)
 * and "where" statement (getWhere)
 * the SQLString class combines it all into resulting search statement
 */
public interface SearchPattern {
    String getColumns();
    String getJoins();
    String getWhere();
}
