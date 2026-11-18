
package com.wanzry.gamebooster;

import android.app.Activity;
import android.os.Bundle;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.*;
import android.animation.ObjectAnimator;

public class MainActivity extends Activity {

    LinearLayout root, content;
    TextView profileText;
    String activeProfile = "BALANCED";
    String graphicPreset = "SMOOTH + FPS STABLE";

    final int BG = Color.rgb(5, 10, 23);
    final int CARD = Color.rgb(13, 27, 48);
    final int BLUE = Color.rgb(0, 190, 255);
    final int WHITE = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activeProfile = getPreferences(0)
                .getString("profile", "BALANCED");

        graphicPreset = getPreferences(0)
                .getString("graphic", "SMOOTH + FPS STABLE");

        showHome();
    }

    int dp(int value) {
        return (int)(value * getResources()
                .getDisplayMetrics().density);
    }

    GradientDrawable shape(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(dp(radius));
        d.setStroke(dp(1), Color.rgb(25, 105, 160));
        return d;
    }

    void createPage(String heading) {
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(BG);

        TextView top = new TextView(this);
        top.setText("🐉  WANZRY  /  " + heading);
        top.setTextColor(BLUE);
        top.setTextSize(19);
        top.setTypeface(null, Typeface.BOLD);
        top.setPadding(dp(18), dp(20), dp(12), dp(15));
        root.addView(top);

        ScrollView scroll = new ScrollView(this);
        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(16), dp(8), dp(16), dp(20));
        scroll.addView(content);

        root.addView(scroll, new LinearLayout.LayoutParams(
                -1, 0, 1));

        LinearLayout nav = new LinearLayout(this);
        nav.setGravity(Gravity.CENTER);
        nav.setPadding(dp(5), dp(8), dp(5), dp(8));
        nav.setBackgroundColor(Color.rgb(8, 19, 35));

        navButton(nav, "⌂\nHome", this::showHome);
        navButton(nav, "🎮\nGames", this::showGames);
        navButton(nav, "⚙\nGrafis", this::showGraphics);
        navButton(nav, "☰\nTools", this::showTools);

        root.addView(nav);
        setContentView(root);
    }

    void navButton(LinearLayout nav, String name,
                   Runnable action) {
        TextView b = new TextView(this);
        b.setText(name);
        b.setTextColor(WHITE);
        b.setTextSize(12);
        b.setGravity(Gravity.CENTER);
        b.setPadding(dp(12), dp(7), dp(12), dp(7));
        nav.addView(b, new LinearLayout.LayoutParams(
                0, -2, 1));
        b.setOnClickListener(v -> action.run());
    }

    TextView text(String value, int size, int color) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setPadding(dp(5), dp(8), dp(5), dp(8));
        return t;
    }

    void addTitle(String value) {
        TextView t = text(value, 21, WHITE);
        t.setTypeface(null, Typeface.BOLD);
        content.addView(t);
    }

    void addCard(String title, String description,
                 Runnable action) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(16), dp(12), dp(16), dp(12));
        card.setBackground(shape(CARD, 17));

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(-1, -2);
        p.setMargins(0, dp(7), 0, dp(7));
        content.addView(card, p);

        TextView a = text(title, 17, BLUE);
        a.setTypeface(null, Typeface.BOLD);
        card.addView(a);

        card.addView(text(description, 13,
                Color.LTGRAY));

        card.setOnClickListener(v -> action.run());
    }

    void addAction(String label, Runnable action) {
        TextView b = text(label, 15, WHITE);
        b.setGravity(Gravity.CENTER);
        b.setTypeface(null, Typeface.BOLD);
        b.setBackground(shape(
                Color.rgb(13, 74, 120), 14));

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(-1, dp(52));
        p.setMargins(0, dp(6), 0, dp(6));
        content.addView(b, p);
        b.setOnClickListener(v -> action.run());
    }

    void showHome() {
        createPage("HOME");

        TextView dragon = text("🐉", 75, BLUE);
        dragon.setGravity(Gravity.CENTER);
        content.addView(dragon);

        ObjectAnimator pulse = ObjectAnimator.ofFloat(
                dragon, "scaleX", 0.88f, 1.12f, 0.88f);
        pulse.setDuration(2200);
        pulse.setRepeatCount(ObjectAnimator.INFINITE);
        pulse.setInterpolator(new DecelerateInterpolator());
        pulse.start();

        TextView name = text("WANZRY", 32, WHITE);
        name.setGravity(Gravity.CENTER);
        name.setTypeface(null, Typeface.BOLD);
        content.addView(name);

        TextView sub = text("ULTIMATE GAME SPACE",
                13, BLUE);
        sub.setGravity(Gravity.CENTER);
        content.addView(sub);

        addCard("⚡ QUICK BOOST",
                "Periksa RAM perangkat dan informasi sistem.",
                this::ramInfo);

        addCard("🎮 GAME SPACE",
                "Lihat daftar game favorit kamu.",
                this::showGames);

        addCard("🎨 GRAPHIC SETTINGS",
                "Pilih preset tampilan dan profil grafis.",
                this::showGraphics);

        addCard("🚀 GAME PROFILE",
                "Profil aktif: " + activeProfile,
                this::chooseProfile);

        addCard("📱 DEVICE MONITOR",
                "Lihat informasi perangkat dan RAM.",
                this::deviceInfo);

        content.addView(text(
                "WANZRY GAME BOOSTER  •  VERSION 2.0",
                11, Color.GRAY));
    }

    void showGames() {
        createPage("GAME SPACE");
        addTitle("MY GAME LIBRARY");

        addCard("🔥 FREE FIRE",
                "Battle Royale • Profil grafis",
                this::showGraphics);

        addCard("⚔ MOBILE LEGENDS",
                "MOBA • Profil grafis",
                this::showGraphics);

        addCard("🎮 ROBLOX",
                "Adventure • Profil grafis",
                this::showGraphics);

        addCard("⛏ MINECRAFT",
                "Sandbox • Profil grafis",
                this::showGraphics);

        addAction("+ TAMBAH GAME", () ->
                message("GAME LIBRARY",
                "Daftar game dapat dikembangkan pada versi berikutnya."));
    }

    void showGraphics() {
        createPage("GRAPHICS");

        TextView dragon = text("🐉", 76, BLUE);
        dragon.setGravity(Gravity.CENTER);
        content.addView(dragon);

        ObjectAnimator rotate = ObjectAnimator.ofFloat(
                dragon, "rotation", -8f, 8f, -8f);
        rotate.setDuration(2600);
        rotate.setRepeatCount(ObjectAnimator.INFINITE);
        rotate.start();

        addTitle("GRAPHIC CONTROL");
        content.addView(text(
                "Pilih preset tampilan yang sesuai dengan perangkat.",
                13, Color.LTGRAY));

        addCard("🟢 SMOOTH + FPS STABLE",
                "Prioritas kelancaran dengan kualitas sederhana.",
                () -> setGraphic("SMOOTH + FPS STABLE"));

        addCard("🔵 BALANCED",
                "Keseimbangan kualitas dan kelancaran.",
                () -> setGraphic("BALANCED"));

        addCard("🟣 HIGH QUALITY",
                "Rekomendasi kualitas visual lebih tinggi.",
                () -> setGraphic("HIGH QUALITY"));

        addCard("🔥 PERFORMANCE",
                "Profil performa untuk game yang didukung.",
                () -> setGraphic("PERFORMANCE"));

        addCard("🌙 BATTERY SAVER",
                "Profil hemat daya.",
                () -> setGraphic("BATTERY SAVER"));

        addCard("📊 PRESET AKTIF",
                graphicPreset,
                () -> message("PRESET AKTIF",
                        graphicPreset));

        content.addView(text(
                "Catatan: Preset ini menyimpan rekomendasi di aplikasi. " +
                "Pengaturan internal Free Fire atau game lain tidak " +
                "berubah otomatis.",
                12, Color.LTGRAY));
    }

    void setGraphic(String preset) {
        graphicPreset = preset;
        getPreferences(0).edit()
                .putString("graphic", preset).apply();

        message("WANZRY GRAPHICS",
                "Preset dipilih: " + preset +
                "\n\nPreset tersimpan di aplikasi.");
        showGraphics();
    }

    void showTools() {
        createPage("TOOLS");
        addTitle("DEVICE TOOLS");

        addCard("⚡ QUICK BOOST",
                "Pemeriksaan RAM tersedia.",
                this::ramInfo);

        addCard("📱 DEVICE INFO",
                "Informasi model dan Android.",
                this::deviceInfo);

        addCard("🎯 GAME PROFILE",
                "Ubah profil permainan.",
                this::chooseProfile);

        addCard("📊 RAM MONITOR",
                "Lihat RAM total dan tersedia.",
                this::ramInfo);
    }

    void chooseProfile() {
        String[] choices = {
                "FPS STABLE",
                "BALANCED",
                "PERFORMANCE",
                "BATTERY SAVER"
        };

        new android.app.AlertDialog.Builder(this)
                .setTitle("SELECT GAME PROFILE")
                .setSingleChoiceItems(choices,
                        java.util.Arrays.asList(choices)
                                .indexOf(activeProfile),
                        (dialog, which) -> {
                            activeProfile = choices[which];

                            getPreferences(0).edit()
                                    .putString("profile",
                                            activeProfile)
                                    .apply();

                            dialog.dismiss();
                            showHome();
                        })
                .setNegativeButton("CLOSE", null)
                .show();
    }

    void ramInfo() {
        ActivityManager am =
                (ActivityManager) getSystemService(
                        Context.ACTIVITY_SERVICE);

        ActivityManager.MemoryInfo mi =
                new ActivityManager.MemoryInfo();

        am.getMemoryInfo(mi);

        message("WANZRY RAM MONITOR",
                "Total RAM: " +
                (mi.totalMem / 1048576) + " MB\n" +
                "RAM tersedia: " +
                (mi.availMem / 1048576) + " MB\n\n" +
                "Pemeriksaan selesai.");
    }

    void deviceInfo() {
        message("DEVICE INFO",
                "Model: " + android.os.Build.MODEL +
                "\nAndroid: " +
                android.os.Build.VERSION.RELEASE +
                "\n\nWANZRY GAME BOOSTER");
    }

    void message(String title, String description) {
        new android.app.AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(description)
                .setPositiveButton("OK", null)
                .show();
    }
}
