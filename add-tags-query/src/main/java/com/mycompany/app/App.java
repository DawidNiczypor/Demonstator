package com.mycompany.app;

import com.microsoft.azure.sdk.iot.service.devicetwin.*;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
public class App 
{
    public static final String iotHubConnectionString = "HostName=iothub-qplg6y.azure-devices.net;SharedAccessKeyName=Dashboard-demo;SharedAccessKey=5ZHi15vTzD5K+rtiujiH4vayFXPgW1PMLyLi7k68V04=";
    public static final String deviceId = "Jetson-Tightening-Cell";


    public static void main( String[] args ) throws IOException
    {
        // Get the DeviceTwin and DeviceTwinDevice objects
        DeviceTwin twinClient = DeviceTwin.createFromConnectionString(iotHubConnectionString);
        DeviceTwinDevice device = new DeviceTwinDevice(deviceId);
// Query the device twins in IoT Hub
        try {
            twinClient.getTwin(device);
            System.out.println(device);
            System.out.println("Devices in Jetson:");

// Construct the query
            SqlQuery sqlQuery = SqlQuery.createSqlQuery("*", SqlQuery.FromType.DEVICES, null, null);

// Run the query, returning a maximum of 10 devices
            Query twinQuery = twinClient.queryTwin(sqlQuery.getQuery(), 10);
            while (twinClient.hasNextDeviceTwin(twinQuery)) {
                DeviceTwinDevice d = twinClient.getNextDeviceTwin(twinQuery);
                System.out.println(d.getDeviceId());
            }

            System.out.println("Devices in Redmond using a cellular network:");

// Construct the query
            sqlQuery = SqlQuery.createSqlQuery("*", SqlQuery.FromType.DEVICES, null, null);

// Run the query, returning a maximum of 10 devices
            twinQuery = twinClient.queryTwin(sqlQuery.getQuery(), 3);
            while (twinClient.hasNextDeviceTwin(twinQuery)) {
                DeviceTwinDevice d = twinClient.getNextDeviceTwin(twinQuery);
                System.out.println(d.getDeviceId());
            }
        }catch (IotHubException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
