# Kala-Kashta (काष्ठ-कला) 🪵✨
### *The Digital Artisan Catalog & Material Estimator*

**Kala-Kashta** is a high-fidelity Android application designed specifically for local carpenters and micro-furniture businesses. It bridges the gap between traditional craftsmanship and modern design expectations by providing a fluid, visual-heavy catalog, a mathematical estimation engine, and a digital project portfolio.

---

## 🚀 Key Features

### 🎨 1. Inspiration Engine (Catalog)
- **Immersive Design Feed:** A staggered masonry grid powered by the Unsplash API (with offline demo fallback).
- **Modern Archetypes:** Categories for Sofas, Beds, Cabinets, and more.
- **Interactive UX:** Custom spring-physics animations for "liking" designs.

### 📐 2. The Workbench (Estimator)
- **Mathematical Precision:** Instant conversion of centimeter dimensions to Square Feet (sq.ft).
- **Timber Selector:** Preset rates for Teak (सागौन), Sheesham (शीशम), Plywood, and MDF.
- **Visual Feedback:** A glowing calculate button and recessed "Gauge" style inputs.

### 🧾 3. Digital Ledger (Quoting)
- **Live Recalculation:** Adjust profit margins on-the-fly with a visual slider.
- **Ticking Totals:** Prices animate to their final value for a premium feel.
- **Room Persistence:** Save invoices to a local database for future reference.

### 📸 4. The Craftbook (Portfolio)
- **Polaroid Showcase:** Add photos of finished projects directly from the camera or gallery.
- **Offline Storage:** Photos are stored locally and rendered in a classic Polaroid card style.

---

## 🛠️ Tech Stack & Architecture

- **UI Framework:** 100% Jetpack Compose (Declarative UI).
- **Design System:** Custom **ArtisanTheme** (Noir Ebony & Electric Teal palette).
- **Navigation:** Compose Navigation with animated transitions.
- **Database:** Room Persistence Library (KSP).
- **Network:** Retrofit + OkHttp + GSON.
- **Image Loading:** Coil `SubcomposeAsyncImage` with custom shimmer effects.
- **State Management:** MVVM with StateFlow & `collectAsStateWithLifecycle`.

---

## 📦 How to Build

1. **Clone the Repo:**
   ```bash
   git clone <your-repo-url>
   ```
2. **Setup API Key (Optional):**
   Add your Unsplash key to `local.properties`:
   ```properties
   UNSPLASH_ACCESS_KEY=your_key_here
   ```
3. **Open in Android Studio:**
   Sync Gradle and run on a device with API level 26+.

---

## 🌐 Web Preview (Visual Twin)
The project also includes a **Web Demo** in the `/web-demo` folder. This is a high-fidelity visual prototype built with Vanilla JS/CSS that mirrors the app's design language.

> **Live Demo:** [Insert your Netlify/Vercel Link Here]

---

## 👨‍💻 Built for
*Android Developer Internship - Artisan Tech Prototype*

*"Traditional art meets modern code."*
