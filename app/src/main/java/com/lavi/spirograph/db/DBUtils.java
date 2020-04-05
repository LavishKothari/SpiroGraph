package com.lavi.spirograph.db;

public class DBUtils {
    private static CoordinateDB coordinateDB;
    private static FavouritesDB favouritesDB;

    public static CoordinateDB getCoordinateDB() {
        return coordinateDB;
    }

    public static void setCoordinateDB(CoordinateDB coordinateDB) {
        DBUtils.coordinateDB = coordinateDB;
    }

    public static FavouritesDB getFavouritesDB() {
        return favouritesDB;
    }

    public static void setFavouritesDB(FavouritesDB favouritesDB) {
        DBUtils.favouritesDB = favouritesDB;
    }
}
