package com.maciejwozny.nextbikeplanner.station;

import java.util.ArrayList;

public interface IStationFactory {
    ArrayList<IStation> createStationList();
}
