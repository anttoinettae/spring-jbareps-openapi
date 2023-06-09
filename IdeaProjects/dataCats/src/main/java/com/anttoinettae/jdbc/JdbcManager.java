package com.anttoinettae.jdbc;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcManager {
    private final Connection connection;

    public JdbcManager() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost/catsData",
                "postgres", "Anta6285471");
    }

    public JdbcCat save(@NotNull JdbcCat jcat) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO cat (name, birth_date, breed, owner_id)" +
                                                                  "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, jcat.name);
        statement.setDate(2, java.sql.Date.valueOf(jcat.birth_date.toString()));
        statement.setString(3, jcat.breed);
        statement.setLong(4, jcat.owner_id);


        statement.executeUpdate();

        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();

        jcat.setId(resultSet.getLong(1));
        return jcat;
    }
    public JdbcOwner save(@NotNull JdbcOwner jowner) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO owner (name, birth_date)" +
                                                                  "VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, jowner.name);
        statement.setDate(2, java.sql.Date.valueOf(jowner.birth_date.toString()));


        statement.executeUpdate();

        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();

        jowner.setId(resultSet.getLong(1));
        return jowner;
    }

    public void deleteCatById(long idToDelete) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM cat WHERE id = ?");

        statement.setLong(1, idToDelete);

        statement.executeUpdate();
    }

    public void deleteOwnerById(long idToDelete) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM owner WHERE id = ?");

        statement.setLong(1, idToDelete);

        statement.executeUpdate();
    }

    public void deleteByEntity(@NotNull JdbcCat jcat) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM cat WHERE id = ?");

        statement.setLong(1, jcat.getId());

        statement.executeUpdate();
    }
    public void deleteByEntity(@NotNull JdbcOwner jowner) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM owner WHERE id = ?");

        statement.setLong(1, jowner.getId());

        statement.executeUpdate();
    }

    public void deleteAll() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE * FROM cat");
        statement.executeUpdate("DELETE * FROM owner");
    }

    public JdbcCat update(@NotNull JdbcCat jcat) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE cat SET " +
                "name = ?," +
                "birth_date = ?," +
                "breed = ?," +
                "owner_id = ?" +
                "WHERE id = ?");

        statement.setString(1, jcat.name);
        statement.setDate(2, java.sql.Date.valueOf(jcat.birth_date.toString()));
        statement.setString(3, jcat.breed);
        statement.setLong(4, jcat.owner_id);
        statement.setLong(5, jcat.getId());

        statement.executeUpdate();

        return jcat;
    }
    public JdbcOwner update(@NotNull JdbcOwner jowner) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE owner SET " +
                "name = ?," +
                "birth_date = ?" +
                "WHERE id = ?");

        statement.setString(1, jowner.name);
        statement.setDate(2, java.sql.Date.valueOf(jowner.birth_date.toString()));
        statement.setLong(3, jowner.getId());

        statement.executeUpdate();
        return jowner;
    }

    public List<JdbcCat> getAllCats() throws SQLException {
        List<JdbcCat> result = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM cat");

        return getJdbcCats(result, results);
    }
    public List<JdbcOwner> getAllOwners() throws SQLException {
        List<JdbcOwner> result = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM owner");

        while (results.next()) {
            Long id = results.getLong(1);
            String name = results.getString(2);
            Date birth_date = results.getDate(3);
            JdbcOwner owner = new JdbcOwner(name, birth_date);
            owner.setId(id);
            result.add(owner);
        }

        return result;
    }

    public List<JdbcCat> getAllByOwnerId(Long ownerId) throws SQLException {
        List<JdbcCat> result = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM cat" +
                                                                  "WHERE id = ?");

        statement.setLong(1, ownerId);

        ResultSet results = statement.executeQuery();
        return getJdbcCats(result, results);
    }

    @Contract("_, _ -> param1")
    private List<JdbcCat> getJdbcCats(List<JdbcCat> result, @NotNull ResultSet results) throws SQLException {
        while (results.next()) {
            Long id = results.getLong(1);
            String name = results.getString(2);
            Date birth_date = results.getDate(3);
            String breed = results.getString(4);
            Long owner_id = results.getLong(5);
            JdbcCat cat = new JdbcCat(name, birth_date, breed, owner_id);
            cat.setId(id);
            result.add(cat);
        }

        return result;
    }

    public void close() throws SQLException {
        connection.close();
    }
}
