package org.yakovlev;

import java.sql.*;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;


public class OracleConnector {
    private Connection connection;

    public boolean connect(String ip, String port, String sid, String login, String password)
    {

        String url = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + sid;
        Properties props = new Properties();

        props.setProperty("user", login);
        props.setProperty("password", password);

        TimeZone timeZone = TimeZone.getTimeZone("GMT+7");
        TimeZone.setDefault(timeZone);
        Locale.setDefault(Locale.ENGLISH);

        try {
            connection = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            return false;
        }

        return true;
    }

    public void close()
    {
        try {
            connection.close();
        } catch (SQLException e) {
            /*IGNORE*/
        }
    }

    public Connection getConnection() {
        return connection;
    }

    private int executeQuery(String query) throws SQLException {
        return connection.createStatement().executeUpdate(query);
    }

//    private void executeQuery(PreparedStatement statement) throws SQLException {
//
//
//        return connection.createStatement().executeUpdate(query);
//    }


    public void dropTables() throws SQLException {
        executeQuery("DROP TABLE free_plates");
        executeQuery("DROP TABLE car_types");
        executeQuery("DROP TABLE plates");
        executeQuery("DROP TABLE organizations");
        executeQuery("DROP TABLE car_brands");
    }

    public void initDatabase() throws SQLException {

        String carBrandsTableQuery = "CREATE TABLE car_brands (" +
                "id number(8) CONSTRAINT car_brands_id PRIMARY KEY, " +
                "brand_name varchar2(100) CONSTRAINT car_brands_brand_name CHECK (brand_name IS NOT NULL), " +
                "alarm_system varchar2(100) CONSTRAINT car_brands_alarm_system CHECK (alarm_system IS NOT NULL))";
        executeQuery(carBrandsTableQuery);

        String organizationsTableQuery = "CREATE TABLE organizations (" +
                "id number(8) CONSTRAINT organizations_id PRIMARY KEY, " +
                "name varchar2(100) CONSTRAINT organizations_name CHECK (name IS NOT NULL), " +
                "address varchar2(100) CONSTRAINT organizations_address CHECK (address IS NOT NULL), " +
                "dir_full_name varchar2(100) CONSTRAINT organizations_dir_full_name CHECK (dir_full_name IS NOT NULL))";
        executeQuery(organizationsTableQuery);

        String platesTableQuery = "CREATE TABLE plates (" +
                "id number(8) CONSTRAINT plates_id PRIMARY KEY, " +
                "plate varchar2(8) CONSTRAINT plates_plate CHECK (plate IS NOT NULL), " +
                "owner_full_name varchar2(100) CONSTRAINT plates_owner_full_name CHECK (owner_full_name IS NOT NULL), " +
                "owner_address varchar2(100) CONSTRAINT plates_owner_address CHECK (owner_address IS NOT NULL), " +
                "brand_id CONSTRAINT FK_brand_id REFERENCES car_brands(id), " +
                "release_date date, " +
                "engine_capacity number(7, 2) CONSTRAINT plates_engine_capacity CHECK (engine_capacity >= 0), " +
                "engine_number varchar2(8) CONSTRAINT plates_engine_number CHECK (engine_number IS NOT NULL), " +
                "chassis_number varchar2(8) CONSTRAINT plates_chassis_number CHECK (chassis_number IS NOT NULL), " +
                "body_number varchar2(8) CONSTRAINT plates_body_number CHECK (body_number IS NOT NULL), " +
                "color varchar2(6) CONSTRAINT plates_color CHECK (color IS NOT NULL), " +
                "for_organization number(1), " +
                "organization_id CONSTRAINT FK_organization_id REFERENCES organizations(id), " +
                "addition_date date)";
        executeQuery(platesTableQuery);

        String carTypesTableQuery = "CREATE TABLE car_types (" +
                "id number(8) CONSTRAINT car_types_id PRIMARY KEY, " +
                "name varchar2(100) CONSTRAINT car_types_name CHECK (name IS NOT NULL))";
        executeQuery(carTypesTableQuery);

        String freePlatesTableQuery = "CREATE TABLE free_plates (" +
                "id number(8) CONSTRAINT free_plates_id PRIMARY KEY, " +
                "series varchar2(4) CONSTRAINT free_plates_series CHECK (series IS NOT NULL), " +
                "start_range number(4) CONSTRAINT free_plates_start_range CHECK (start_range >= 0 AND start_range < 10000), " +
                "end_range number(4) CONSTRAINT free_plates_end_range CHECK (end_range >= 0 AND end_range < 10000), " +
                "car_type_id CONSTRAINT FK_car_type_id REFERENCES car_types(id))";
        executeQuery(freePlatesTableQuery);

        String TechnicalInspectionsTableQuery = "CREATE TABLE technical_inspections (" +
                "id number(8) CONSTRAINT ti_id PRIMARY KEY, " +
                "plate_id CONSTRAINT FK_plate_id REFERENCES plates(id), " +
                "is_passed number(4) CONSTRAINT ti_is_passed CHECK (is_passed = 0 OR is_passed = 1))";
        executeQuery(TechnicalInspectionsTableQuery);

        String WantedCarTableQuery = "CREATE TABLE wanted_cars (" +
                "id number(8) CONSTRAINT wanted_cars_id PRIMARY KEY, " +
                "plate_id CONSTRAINT FK_wc_plate_id REFERENCES plates(id), " +
                "theft_date date, " +
                "is_found number(4) CONSTRAINT wanted_cars_is_found CHECK (is_found = 0 OR is_found = 1))";
        executeQuery(WantedCarTableQuery);

        String TaTypesTableQuery = "CREATE TABLE ta_types (" +
                "id number(8) CONSTRAINT ta_types_id PRIMARY KEY, " +
                "name varchar2(100) CONSTRAINT ta_types_name CHECK (name IS NOT NULL))";
        executeQuery(TaTypesTableQuery);

        String TrafficAccidentTableQuery = "CREATE TABLE traffic_accidents (" +
                "id number(8) CONSTRAINT ta_wanted_cars_id PRIMARY KEY, " +
                "ta_date date, " +
                "ta_type_id CONSTRAINT FK_ta_ta_type_id REFERENCES ta_types(id), " +
                "place varchar2(100) CONSTRAINT ta_place CHECK (place IS NOT NULL), " +
                "description varchar2(100) CONSTRAINT ta_description CHECK (description IS NOT NULL), " +
                "victims_number number(4) CONSTRAINT ta_victims_number CHECK (victims_number >= 0 AND victims_number < 10000), " +
                "damage_amount number(7, 2) CONSTRAINT ta_damage_amount CHECK (damage_amount >= 0), " +
                "reason varchar2(100) CONSTRAINT ta_reason CHECK (reason IS NOT NULL), " +
                "road_conditions varchar2(100) CONSTRAINT ta_road_conditions CHECK (road_conditions IS NOT NULL))";
        executeQuery(TrafficAccidentTableQuery);

        String TaAffectedVehiclesTableQuery = "CREATE TABLE ta_affected_vehicles (" +
                "id number(8) CONSTRAINT taav_id PRIMARY KEY, " +
                "ta_id CONSTRAINT FK_taav_ta_id REFERENCES traffic_accidents(id), " +
                "plate_id CONSTRAINT FK_taav_plate_id REFERENCES plates(id))";
        executeQuery(TaAffectedVehiclesTableQuery);
    }
}
