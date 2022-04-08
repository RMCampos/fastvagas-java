package fastvagas.data.dao;

import fastvagas.data.entity.User;
import fastvagas.data.entity.UserTerm;
import fastvagas.data.mapper.UserTermRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserTermDao extends Dao<UserTerm> {

    public UserTermDao(NamedParameterJdbcTemplate template) {
        super(UserTerm.class, template, new UserTermRowMapper());
    }

    public UserTerm findById(Long user_id) {
        final String query = "SELECT * FROM " + UserTerm.TABLE
            + " WHERE " + UserTerm.USER_ID + "=:" + UserTerm.USER_ID;

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue(UserTerm.USER_ID, user_id);

        return getObjectFromResult(query, params);
    }

    public List<UserTerm> findAll() {
        return getListFromResult("SELECT * FROM " + UserTerm.TABLE);
    }

    public List<UserTerm> findAllByUserId(Long user_id) {
        final String query = "SELECT * "
                + " FROM " + UserTerm.TABLE
                + " WHERE " + UserTerm.USER_ID + "= :" + UserTerm.USER_ID;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(UserTerm.USER_ID, user_id);

        return getListFromResult(query, params);
    }

    public List<UserTerm> findAllEnabledUsersTerms() {
        final String query = "SELECT " + UserTerm.TABLE + ".*"
                + " FROM " + UserTerm.TABLE
                + " JOIN " + User.TABLE + " ON ("
                + User.TABLE + "." + User.USER_ID + "=" + UserTerm.TABLE + "." + UserTerm.USER_ID
                + ")"
                + " WHERE " + User.ENABLED + "= 1";

        return getListFromResult(query);
    }

    public UserTerm create(UserTerm userTerm) {
        final String query = "INSERT INTO " + UserTerm.TABLE + " ("
            + UserTerm.TERMS
            + ") values ("
            + ":" + UserTerm.TERMS
            + ")";

        if (executeInsert(query, getParams(userTerm)) == 1) {
            userTerm.setUser_id(getGeneratedId(UserTerm.USER_ID));
            return userTerm;
        }

        return null;
    }

    public UserTerm update(UserTerm userTerm) {
        final String query = "UPDATE " + UserTerm.TABLE
            + " SET " + UserTerm.TERMS + "=:" + UserTerm.TERMS
            + " WHERE " + UserTerm.USER_ID + "=:" + UserTerm.USER_ID;

        if (executeUpdateDelete(query, getParams(userTerm)) == 1) {
            return userTerm;
        }

        return null;
    }

    public UserTerm deleteById(UserTerm userTerm) {
        final String query = "DELETE FROM " + UserTerm.TABLE
            + " WHERE " + UserTerm.USER_ID + "=:" + UserTerm.USER_ID;

        if (executeUpdateDelete(query, getParams(userTerm)) == 1) {
            return userTerm;
        }

        return null;
    }

    private Map<String, Object> getParams(UserTerm userTerm) {
        Map<String, Object> params = new HashMap<>();
        params.put(UserTerm.USER_ID, userTerm.getUser_id());
        params.put(UserTerm.TERMS, userTerm.getTerms());
        return params;
    }
}
