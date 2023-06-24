package com.loto188.gameboost;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StatFs;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BoostAllFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_device_specs, container, false);

        TextView deviceModelTextView = rootView.findViewById(R.id.text_view_device_model);
        TextView deviceManufacturerTextView = rootView.findViewById(R.id.text_view_device_manufacturer);
        TextView deviceOSVersionTextView = rootView.findViewById(R.id.text_view_device_os_version);
        TextView ramRemainingTextView = rootView.findViewById(R.id.text_view_ram_remaining);
        TextView storageTextView = rootView.findViewById(R.id.text_view_storage);
        TextView storageRemainingTextView = rootView.findViewById(R.id.text_view_storage_remaining);
        TextView cpuTextView = rootView.findViewById(R.id.text_view_cpu);
        TextView cpuUsageTextView = rootView.findViewById(R.id.text_view_cpu_usage);

        String deviceModel = Build.MODEL;
        String deviceManufacturer = Build.MANUFACTURER;
        String deviceOSVersion = Build.VERSION.RELEASE;
        String ramRemaining = getRAMRemaining();
        String storage = getTotalInternalStorage();
        String storageRemaining = getInternalStorageRemaining();
        String cpu = getCpuName();
        String cpuUsage = getCpuUsage();

        deviceModelTextView.setText(String.format("%s%s", getString(R.string.device_model), deviceModel));
        deviceManufacturerTextView.setText(String.format("%s%s", getString(R.string.device_manufacturer), deviceManufacturer));
        deviceOSVersionTextView.setText(String.format("%s%s", getString(R.string.device_os_version), deviceOSVersion));
        ramRemainingTextView.setText(String.format("%s%s", getString(R.string.ram_remaining), ramRemaining));
        storageTextView.setText(String.format("%s%s", getString(R.string.storage), storage));
        storageRemainingTextView.setText(String.format("%s%s", getString(R.string.storage_remaining), storageRemaining));
        cpuTextView.setText(String.format("%s%s", getString(R.string.cpu), cpu));
        cpuUsageTextView.setText(String.format("%s%s", getString(R.string.cpu_usage), cpuUsage));

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                BoostFragment boostFragment = new BoostFragment();

                TextView toastTextView = new TextView(requireContext());
                toastTextView.setText(R.string.boost_successful);
                toastTextView.setTextColor(Color.WHITE);
                toastTextView.setTextSize(18);
                toastTextView.setTypeface(null, Typeface.BOLD);
                toastTextView.setGravity(Gravity.CENTER);
                toastTextView.setPadding(30, 20, 30, 20);
                toastTextView.setBackgroundResource(R.drawable.grid_background);

                Toast toast = new Toast(requireContext());
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(toastTextView);

                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 250);

                toast.show();

                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_container, boostFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }, 4000);

        return rootView;
    }

    private String getRAMRemaining() {
        Context context = requireContext().getApplicationContext();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        long ramRemaining = memoryInfo.availMem;
        return Formatter.formatFileSize(context, ramRemaining);
    }

    private String getTotalInternalStorage() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        long totalSize = blockSize * totalBlocks;
        return Formatter.formatFileSize(requireContext(), totalSize);
    }

    private String getInternalStorageRemaining() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        long availableSize = blockSize * availableBlocks;
        return Formatter.formatFileSize(requireContext(), availableSize);
    }

    private String getCpuName() {
        String[] abis = Build.SUPPORTED_ABIS;
        if (abis != null && abis.length > 0) {
            return abis[0];
        }
        return Build.CPU_ABI;
    }

    private String getCpuUsage() {
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
            String line = reader.readLine();
            reader.close();

            String[] cpuStats = line.split(" ");
            long idleTime = Long.parseLong(cpuStats[4]);
            long totalTime = 0;

            for (String stat : cpuStats) {
                if (!stat.isEmpty() && Character.isDigit(stat.charAt(0))) {
                    totalTime += Long.parseLong(stat);
                }
            }
            long totalIdleTime = idleTime + Long.parseLong(cpuStats[5]);
            long usedTime = totalTime - totalIdleTime;

            double cpuUsage = (double) usedTime / totalTime;
            int cpuPercentage = (int) (cpuUsage * 100);

            return cpuPercentage + "%";
        } catch (IOException e) {
            e.printStackTrace();
            return "N/A";
        }
    }

}

