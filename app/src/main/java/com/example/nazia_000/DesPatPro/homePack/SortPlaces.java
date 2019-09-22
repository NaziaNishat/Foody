package com.example.nazia_000.DesPatPro.homePack;

import com.example.nazia_000.DesPatPro.classPack.ProfilesClass;
import com.google.android.gms.maps.model.LatLng;

import java.util.Comparator;

public class SortPlaces implements Comparator<ProfilesClass> {
    LatLng currentLoc;

    public SortPlaces(LatLng current){
        currentLoc = current;
    }
    @Override
    public int compare(ProfilesClass p1, ProfilesClass p2) {
        double lat1 = p1.getLat();
        double lon1 = p1.getLon();
        double lat2 = p2.getLat();
        double lon2 = p2.getLon();

        double distanceToPlace1 = distance(currentLoc.latitude, currentLoc.longitude, lat1, lon1);
        double distanceToPlace2 = distance(currentLoc.latitude, currentLoc.longitude, lat2, lon2);
        return (int) (distanceToPlace1 - distanceToPlace2);
    }

    public double distance(double fromLat, double fromLon, double toLat, double toLon) {

        double radius = 6378137;   // approximate Earth radius, *in meters*
        double deltaLat = toLat - fromLat;
        double deltaLon = toLon - fromLon;
        double angle = 2 * Math.asin( Math.sqrt(
                Math.pow(Math.sin(deltaLat/2), 2) +
                        Math.cos(fromLat) * Math.cos(toLat) *
                                Math.pow(Math.sin(deltaLon/2), 2) ) );
        return radius * angle;

        //return Math.sqrt((fromLat - toLat)+(fromLon - toLon));

    }

}
