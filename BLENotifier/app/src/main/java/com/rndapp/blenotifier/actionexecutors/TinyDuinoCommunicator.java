package com.rndapp.blenotifier.actionexecutors;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Set;

/**
 * Created by ell on 12/26/14.
 */
public class TinyDuinoCommunicator extends BluetoothGattCallback {
    private Context mContext;
    private BluetoothAdapter mBluetoothAdapter;
    private int mColor;

    public TinyDuinoCommunicator(Context context) {
        mContext = context;

        final BluetoothManager bluetoothManager =
                (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
    }

    public void sendColor(int color){
        mColor = color;

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().contains("TinyDuino")){
                    device.connectGatt(mContext, true, this);
                }
            }
        }
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        switch (newState){
            case BluetoothGatt.STATE_CONNECTED:
                Log.d("TinyDuinoCommunicator", "connected to device");
                if (gatt.getServices() != null && gatt.getServices().size() != 0){
                    onServicesDiscovered(gatt, status);
                }else {
                    gatt.discoverServices();
                }
                break;
            case BluetoothGatt.STATE_CONNECTING:
                Log.d("TinyDuinoCommunicator", "connecting to device");
                break;
            case BluetoothGatt.STATE_DISCONNECTED:
                Log.d("TinyDuinoCommunicator", "disconnected device");
                break;
            case BluetoothGatt.STATE_DISCONNECTING:
                Log.d("TinyDuinoCommunicator", "disconnecting from device");
                break;
        }
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        if (gatt.getServices() != null){
            Log.d("TinyDuinoCommunicator", "services discovered");
            BluetoothGattCharacteristic characteristic = parseServicesForWriteCharacteristic(gatt.getServices());
            if (characteristic != null){
                byte[] array = new byte[3];
                array[0] = (byte)((mColor >> 16) & 0xFF);
                array[1] = (byte)((mColor >> 8) & 0xFF);
                array[2] = (byte)((mColor) & 0xFF);
                Log.d("TinyDuinoCommunicator", Integer.toHexString((int)array[0]));
                Log.d("TinyDuinoCommunicator", Integer.toHexString((int)array[1]));
                Log.d("TinyDuinoCommunicator", Integer.toHexString((int)array[2]));
                characteristic.setValue(array);
                gatt.writeCharacteristic(characteristic);
            }
        }else {
            Log.d("TinyDuinoCommunicator", "no services discovered");
        }
    }

    protected BluetoothGattCharacteristic parseServicesForWriteCharacteristic(List<BluetoothGattService> services){
        BluetoothGattCharacteristic result = null;
        for (BluetoothGattService service : services){
            String uuid = service.getUuid().toString();
            Log.d("TinyDuinoCommunicator", "service: " + uuid);
            if (uuid.toLowerCase().equals("195ae58a-437a-489b-b0cd-b7c9c394bae4")){
                for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()){
                    String characteristicUuid = characteristic.getUuid().toString();
                    Log.d("TinyDuinoCommunicator", "characteristic: " + characteristicUuid);
                    if (characteristicUuid.toLowerCase().equals("5fc569a0-74a9-4fa4-b8b7-8354c86e45a4")){
                        result = characteristic;
                    }
                }
            }

        }
        return result;
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicWrite(gatt, characteristic, status);
        if (status == BluetoothGatt.GATT_SUCCESS){
            Log.d("TinyDuinoCommunicator", "WIN");
        }else {
            Log.d("TinyDuinoCommunicator", "FAIL");
        }

    }
}
