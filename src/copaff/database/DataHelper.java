package copaff.database;

import copaff.model.Clan;
import copaff.model.Player;
import copaff.model.Squad;
import copaff.model.Team;
import copaff.model.match_modes.Scrimmage;
import copaff.model.relations.FixedSquad;
import copaff.model.relations.Game;
import copaff.model.relations.SquadAlternate;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author afsal
 */
public class DataHelper {

    private final static Logger LOGGER = LogManager.getLogger(DatabaseHandler.class.getName());

    /**
     * @param tableType Type of table to create: CLAN, TEAM, SQUAD or other type
     * that has its ID as name and playerID as data.
     * @param tableName Name of the table, usually the ID of the Type of table
     * @return
     */
    public static boolean createTable(String tableType, String tableName) {
        try {
            String sql = "CREATE TABLE " + tableType + tableName.replaceAll("-", "") + " (\n"
                    + "	playerID varchar(200) primary key)";
            //+ "FOREIGN KEY (playerID) REFERENCES PLAYER(id))";
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(sql);
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean insertPLayerInTable(String tableType, String tableName, String playerID) {
        try {
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "INSERT INTO " + tableType + tableName.replaceAll("-", "") + "(playerID) VALUES(?)");
            statement.setString(1, playerID);
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean isPlayerExistsInTable(String tableType, String tableName, String playerID) {
        try {
            String checkstmt = "SELECT COUNT(*) FROM " + tableType + tableName.replaceAll("-", "") + " WHERE id=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
            stmt.setString(1, playerID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(count);
                return (count > 0);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean insertNewClan(Clan clan, FileInputStream fs, int fLength) {
        try {
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "INSERT INTO CLAN(id,name,logo) VALUES(?,?,?)");
            statement.setString(1, clan.getId());
            statement.setString(2, clan.getName());
            statement.setBinaryStream(3, fs, fLength);
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean insertNewPlayer(Player player, FileInputStream fs, int fLength) {
        try {
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "INSERT INTO PLAYER(id,name,country,logo,created) VALUES(?,?,?,?,?)");
            statement.setString(1, player.getId());
            statement.setString(2, player.getName());
            statement.setString(3, player.getCountry());
            statement.setBinaryStream(4, fs, fLength);
            statement.setTimestamp(5, Timestamp.valueOf(player.getCreationDateTime()));
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean insertNewSquad(Squad squad, FileInputStream fs, int fLength) {
        try {
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "INSERT INTO SQUAD(id,name,logo) VALUES(?,?,?)");
            statement.setString(1, squad.getId());
            statement.setString(2, squad.getName());
            statement.setBinaryStream(3, fs, fLength);
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean insertNewTeam(Team team, FileInputStream fs, int fLength) {
        try {
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "INSERT INTO TEAM(id,name,logo) VALUES(?,?,?)");
            statement.setString(1, team.getId());
            statement.setString(2, team.getName());
            statement.setBinaryStream(3, fs, fLength);
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean insertNewScrimmage(Scrimmage scrimmage) {
        try {
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "INSERT INTO SCRIMMAGE(id,customRoomID,creatorID,map,block,created) VALUES(?,?,?,?,?,?)");
            statement.setString(1, scrimmage.getId());
            statement.setString(2, scrimmage.getCustomRoomID());
            statement.setString(3, scrimmage.getCreatorId());
            statement.setString(4, scrimmage.getMap());
            statement.setInt(5, scrimmage.getBlock());
            statement.setTimestamp(6, Timestamp.valueOf(scrimmage.getCreated()));
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean insertNewFixedSquad(FixedSquad fixedSquad) {
        try {
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "INSERT INTO FIXEDSQUAD(playerID) VALUES(?)");
            statement.setString(1, fixedSquad.getPlayerId());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean insertNewMatch(Game match) {
        try {
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "INSERT INTO MATCH(playerID, kills, position) VALUES(?,?,?)");
            statement.setString(1, match.getPlayerId());
            statement.setInt(2, match.getKills());
            statement.setInt(3, match.getPosition());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean insertNewSquadAlternate(SquadAlternate squadAlternate) {
        try {
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "INSERT INTO SQUADALTERNATE(playerID) VALUES(?)");
            statement.setString(1, squadAlternate.getPlayerId());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean isObjectExists(String tableName, String id) {
        try {
            String checkstmt = "SELECT COUNT(*) FROM  " + tableName + " WHERE id=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(count);
                return (count > 0);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean isClanExists(String id) {
        try {
            String checkstmt = "SELECT COUNT(*) FROM CLAN WHERE id=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(count);
                return (count > 0);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    /**
     * @param id is the player's id
     * @return true if such player exist
     *
     */
    public static boolean isPlayerExists(String id) {
        try {
            String checkstmt = "SELECT COUNT(*) FROM PLAYER WHERE id=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(count);
                return (count > 0);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean isSquadExists(String id) {
        try {
            String checkstmt = "SELECT COUNT(*) FROM SQUAD WHERE id=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(count);
                return (count > 0);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean isTeamExists(String id) {
        try {
            String checkstmt = "SELECT COUNT(*) FROM TEAM WHERE id=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(count);
                return (count > 0);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean isClashSquadExists(String id) {
        try {
            String checkstmt = "SELECT COUNT(*) FROM CLASHSQUAD WHERE id=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(count);
                return (count > 0);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean isScrimmageExists(String id) {
        try {
            String checkstmt = "SELECT COUNT(*) FROM SCRIMMAGE WHERE id=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(count);
                return (count > 0);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean isFixedSquadExists(String id) {
        try {
            String checkstmt = "SELECT COUNT(*) FROM FIXEDSQUAD WHERE id=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
            stmt.setString(1, "SQUAD" + id.replaceAll("-", ""));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(count);
                return (count > 0);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean isMatchExists(String id) {
        try {
            String checkstmt = "SELECT COUNT(*) FROM MATCH WHERE id=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(count);
                return (count > 0);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean isSquadAlternateExists(String id) {
        try {
            String checkstmt = "SELECT COUNT(*) FROM SQUADALTERNATE WHERE id=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(count);
                return (count > 0);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static void wipeTable(String tableName) {
        try {
            Statement statement = DatabaseHandler.getInstance().getConnection().createStatement();
            statement.execute("DELETE FROM " + tableName + " WHERE TRUE");
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
    }
}
