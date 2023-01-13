package net.associal.bbooked.entity.searchPattern;

import java.util.Collection;

/**
 * Creates sql sting for search based on a list of SearchPatterns
 */
public class SQLString {
    private String columns;
    private String joins;
    private String where;

    private Collection<SearchPattern> patterns;

    public SQLString() {
        this.columns = "select";
        this.joins = "from Tour";
        this.where = "";
    }

    public SQLString(Collection<SearchPattern> patterns) {
        this();
        this.patterns = patterns;
    }

  /**
   *
   * @return sql
   *
   * Generates sql string based on SearchPatterns used (if any)
   */
  public String generateSql() {
        int iColumns = 0;
        int iJoins = 0;
        int iWhere = 0;

        for(SearchPattern pattern: this.patterns) {
            String columns = pattern.getColumns();
            if(columns != null) {
                if(iColumns > 0) {
                    this.columns += ", " + columns;
                } else {
                    this.columns += " " + columns;
                }
                iColumns++;
            }

            String joins = pattern.getJoins();
            if(joins != null) {
                this.joins += " " + joins;
                iJoins++;
            }

            String where = pattern.getWhere();
            if(where != null) {
                if(iWhere > 0) {
                    this.where += " and " + where;
                } else {
                    this.where += " where " + where;
                }
                iWhere++;
            }
        }

        String sql = this.columns + " " + this.joins + " " + this.where + " LIMIT ?, 100;";
        return sql;
    }


}
