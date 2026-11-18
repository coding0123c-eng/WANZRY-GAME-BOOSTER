
package com.wanzry.gamebooster;

import android.app.Activity;
import android.os.Bundle;
import android.app.ActivityManager;
import android.os.StatFs;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {

    LinearLayout layout;
    TextView profile;
    String activeProfile = "BALANCED";

    int blue = Color.rgb(0, 190, 255);
    int dark = Color.rgb(10, 17, 30);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activeProfile = getPreferences(0)
            .getString("profile", "BALANCED");

        ScrollView scroll = new ScrollView(this);
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(22, 28, 22, 28);
        layout.setBackgroundColor(dark);

        scroll.addView(layout);
        setContentView(scroll);

        TextView title = new TextView(this);
        title.setText("W");
        title.setTextSize(42);
        title.setTextColor(blue);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(null, Typeface.BOLD);
        layout.addView(title);

        addText("WANZRY", 30, Color.WHITE);
        addText("GAME BOOSTER", 15, blue);

        profile = addText("PROFILE AKTIF: " + activeProfile,
            16, Color.WHITE);

        addButton("QUICK BOOST", this::quickBoost);
        addButton("GAME MODE", () ->
            message("GAME MODE", "Mode permainan siap dipilih.")
        );
        addButton("RAM INFO", this::ramInfo);
        addButton("DEVICE INFO", this::deviceInfo);
        addButton("GAME PROFILE", this::chooseProfile);
        addButton("DEVICE MONITOR", this::ramInfo);
        addButton("GRAPHIC SETTINGS", () ->
            message("GRAPHIC SETTINGS",
            "Pengaturan grafik belum diterapkan ke sistem.")
        );

        addText("WANZRY GAME BOOSTER v1.0",
            12, Color.GRAY);
    }

    TextView addText(String text, int size, int color) {
        TextView t = new TextView(this);
        t.setText(text);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER);
        t.setPadding(8, 12, 8, 12);
        layout.addView(t);
        return t;
    }

    void addButton(String name, Runnable action) {
        Button b = new Button(this);
        b.setText(name);
        b.setTextColor(Color.WHITE);
        b.setTextSize(15);
        b.setAllCaps(false);
        b.setBackgroundTintList(
            android.content.res.ColorStateList.valueOf(
                Color.rgb(20, 65, 105)
            )
        );

        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(
                -1, -2
            );
        params.setMargins(0, 8, 0, 8);

        layout.addView(b, params);
        b.setOnClickListener(v -> action.run());
    }

    void message(String title, String text) {
        new android.app.AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(text)
            .setPositiveButton("OK", null)
            .show();
    }

    void quickBoost() {
        ActivityManager am =
            (ActivityManager) getSystemService(
                Context.ACTIVITY_SERVICE
            );

        ActivityManager.MemoryInfo mi =
            new ActivityManager.MemoryInfo();

        am.getMemoryInfo(mi);

        long available = mi.availMem / 1048576;

        message("WANZRY QUICK BOOST",
            "RAM tersedia: " + available + " MB\n\n" +
            "Pemeriksaan selesai.\n" +
            "Tidak ada proses sistem yang dihentikan.");
    }

    void ramInfo() {
        ActivityManager am =
            (ActivityManager) getSystemService(
                Context.ACTIVITY_SERVICE
            );

        ActivityManager.MemoryInfo mi =
            new ActivityManager.MemoryInfo();

        am.getMemoryInfo(mi);

        message("RAM INFO",
            "Total RAM: " +
            (mi.totalMem / 1048576) + " MB\n" +
            "RAM tersedia: " +
            (mi.availMem / 1048576) + " MB");
    }

    void deviceInfo() {
        message("DEVICE INFO",
            "Model: " + android.os.Build.MODEL +
            "\nAndroid: " +
            android.os.Build.VERSION.RELEASE);
    }

    void chooseProfile() {
        String[] choices = {
            "FPS STABLE",
            "BALANCED",
            "PERFORMANCE",
            "BATTERY SAVER"
        };

        new android.app.AlertDialog.Builder(this)
            .setTitle("PILIH GAME PROFILE")
            .setSingleChoiceItems(
                choices,
                java.util.Arrays.asList(choices)
                    .indexOf(activeProfile),
                (dialog, which) -> {
                    activeProfile = choices[which];

                    getPreferences(0).edit()
                        .putString("profile", activeProfile)
                        .apply();

                    profile.setText(
                        "PROFILE AKTIF: " + activeProfile
                    );

                    dialog.dismiss();
                }
            )
            .setNegativeButton("KEMBALI", null)
            .show();
    }
}
