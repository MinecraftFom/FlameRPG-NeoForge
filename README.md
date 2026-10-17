<!-- ============================================================ -->
<!--  FLAME RPG · English technical documentation                   -->
<!--  Document type: ENGINEERING SPECIFICATION                      -->
<!-- ============================================================ -->

<div align="center">

[![📖 Simplfied Chinese Docs](https://img.shields.io/badge/DOCS-Simplfied%20Chinese%20Documentaetion-e8722a?style=for-the-badge)](./README.zh-CN.md)

# 🜂 FLAME RPG

**FRPG — an MMORPG-grade gameplay mod built for multiplayer servers**

[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.1-1f1f1f?style=for-the-badge&logo=minecraft&logoColor=white)](https://www.minecraft.net/)
[![NeoForge](https://img.shields.io/badge/NeoForge-21.1.252-e8722a?style=for-the-badge)](https://neoforged.net/)
[![Java](https://img.shields.io/badge/Java-21-0b4f8a?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![License](https://img.shields.io/badge/License-All%20Rights%20Reserved-8b0000?style=for-the-badge)](./LICENSE)
[![Build](./actions/workflows/build.yml/badge.svg)](./actions/workflows/build.yml)
[![Release](https://img.shields.io/github/v/release/MinecraftFom/FlameRPG-NeoForge?style=for-the-badge&label=release&color=2ea44f)](https://github.com/MinecraftFom/FlameRPG-NeoForge/releases/latest)

</div>

## ▍I. ABSTRACT

> This mod is an RPG mod built for **multiplayer servers**, aiming to deliver a
> **complete MMORPG-grade gameplay experience**.
>
> It bundles **adventure** and **role-playing (RPG)** into a single **integrated experience**.

---

## ▍II. ESSENTIAL LINKS

| Type | Link |
| :--- | :--- |
| Team GitHub | [MinecraftFom](https://github.com/MinecraftFom) |
| Team website | [Fom's Dev Website](https://git.fomdev.com) |
| Fom477 on GitHub | [Fom477](https://github.com/Fom477) |
| Fom477 website | [Fom477 Website](https://fom477.fomdev.com) |
| Source repository | [MinecraftFom/FlameRPG-NeoForge](https://github.com/MinecraftFom/FlameRPG-NeoForge) |
| Handover doc | [HANDOVER.md](./HANDOVER.md) — environment, structure, workflow, troubleshooting |
| Changelog | [CHANGELOG.md](./CHANGELOG.md) |
| License | [LICENSE](./LICENSE) |

---

## ▍III. MOD STRUCTURE

The mod is assembled from **multiple submodules** that work together as one system.

### 3.1 Parent module — `flamerpg`

| Item | Specification |
| :--- | :--- |
| Role | **Parent mod**, owns core assembly and global registration |
| Package | [`com.fomdev.flamerpg`](./src/main/java/com/fomdev/flamerpg) |
| Main class | [`com.fomdev.flamerpg.init.FlameRPG`](./src/main/java/com/fomdev/flamerpg/init/FlameRPG.java) |
| Mod ID | `flamerpg` |
| Mod name | `Flame RPG` |
| Core developer | Fom477 (Fom's Dev) |
| Co-developers | None |

### 3.2 Sub-module — Skills `frpg_skill`

| Item | Specification |
| :--- | :--- |
| Role | Sub-module for the `Skills` system |
| Package | [`com.fomdev.flamerpg.modules.frpg_skill`](./src/main/java/com/fomdev/flamerpg/modules/frpg_skill) |
| Main class | [`com.fomdev.flamerpg.modules.frpg_skill.FRPGSkill`](./src/main/java/com/fomdev/flamerpg/modules/frpg_skill/FRPGSkill.java) |
| Mod ID | `frpg_skill` |
| Mod name | `FlameRPG: Skill` |

---

## ▍IV. TECHNICAL SPECIFICATION

| Item | Value |
| :--- | :--- |
| Minecraft | `1.21.1` |
| Mod loader | NeoForge `21.1.252` (`javafml`) |
| Java toolchain | Java `21` |
| Build plugin | ModDevGradle `2.0.148` |
| Mappings | Parchment `2024.11.17` |
| Mod version | `1.0.0` |
| License | All Rights Reserved |

## | V. MOD AUTHORS

| Part | Developers |
| :--- | :---       |
| Leading Developer | Fom477 |
| Co Developer | ZZY |
| Chinese Translation | ZZY |
| Art | Fom477 |
| Code | Fom477, ZZY |
| Scripts | Fom477 |
| Project Management | Fom477 (Fom's Dev) |

### Build & Run

Run: 
```bash
./gradlew runClient
```
Build:
```bash
./gradlew build
./gradlew jar
```

Artifacts are written to [build/libs/](./build/libs).

---

<div align="center">

**FLAME RPG** · `flamerpg` + `frpg_skill`

*Adventure & RPG — delivered as one*

</div>
