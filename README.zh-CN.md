<!-- ============================================================ -->
<!--  FLAME RPG · 简体中文技术文档                                   -->
<!--  文档类型：工程规格书 / ENGINEERING SPECIFICATION              -->
<!-- ============================================================ -->

<div align="center">

[![📖 Read in English](https://img.shields.io/badge/%F0%9F%93%96-Read%20in%20English-2f81f7?style=for-the-badge)](./README.md)

# 🜂 FLAME RPG

**FRPG — 面向多人在线服务器的 MMORPG 级玩法模组**

[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.1-1f1f1f?style=for-the-badge&logo=minecraft&logoColor=white)](https://www.minecraft.net/)
[![NeoForge](https://img.shields.io/badge/NeoForge-21.1.252-e8722a?style=for-the-badge)](https://neoforged.net/)
[![Java](https://img.shields.io/badge/Java-21-0b4f8a?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![License](https://img.shields.io/badge/License-All%20Rights%20Reserved-8b0000?style=for-the-badge)](https://github.com/zzy89216-gif/FlameRPG-NeoForge_zzy/blob/master/LICENSE)
[![Build](https://github.com/zzy89216-gif/FlameRPG-NeoForge_zzy/actions/workflows/build.yml/badge.svg)](https://github.com/zzy89216-gif/FlameRPG-NeoForge_zzy/actions/workflows/build.yml)
[![Release](https://img.shields.io/github/v/release/zzy89216-gif/FlameRPG-NeoForge_zzy?style=for-the-badge&label=release&color=2ea44f)](https://github.com/zzy89216-gif/FlameRPG-NeoForge_zzy/releases/latest)

</div>

## ▍一、摘要 / ABSTRACT

> 本模组是一款为 **多人在线服务器** 打造的 RPG 模组，目标是为玩家交付 **MMORPG 级别的完整游戏体验**。
>
> 在本模组中，玩家可获得 **冒险** 与 **角色扮演（RPG）** 两条主线的 **一体化融合体验**。

---

## ▍二、核心链接 / ESSENTIAL LINKS

| 资源 | 入口 |
| :--- | :--- |
| 团队 GitHub 主页 | [MinecraftFom](https://github.com/MinecraftFom) |
| 团队官方网站 | [git.fomdev.com](https://git.fomdev.com) |
| Fom477 GitHub 主页 | [Fom477](https://github.com/Fom477) |
| Fom477 个人网站 | [fom477.fomdev.com](https://fom477.fomdev.com) |
| 代码仓库 | [MinecraftFom/FlameRPG-NeoForge](https://github.com/MinecraftFom/FlameRPG-NeoForge) |
| 交接文档 | [HANDOVER.md](./HANDOVER.md) — 环境、结构、流程与排障 |
| 更新日志 | [CHANGELOG.md](./CHANGELOG.md) |
| 授权声明 | [LICENSE](./LICENSE) |

---

## ▍三、模组架构 / MOD STRUCTURE

本模组采用 **模块化装配结构**，由多个子模块（sub-module）协同构成。

### 3.1 主模组 — `flamerpg`

| 项目 | 规格 |
| :--- | :--- |
| 定位 | **父模组（Parent Mod）**，承担核心装配与全局注册 |
| 包路径 | [`com.fomdev.flamerpg`](./src/main/java/com/fomdev/flamerpg) |
| 主类 | [`com.fomdev.flamerpg.init.FlameRPG`](./src/main/java/com/fomdev/flamerpg/init/FlameRPG.java) |
| Mod ID | `flamerpg` |
| 模组名称 | `Flame RPG` |
| 核心开发 | Fom477（Fom's Dev） |
| 联合开发 | 无 |

### 3.2 子模块 — 技能系统 `frpg_skill`

| 项目 | 规格 |
| :--- | :--- |
| 定位 | `Skills`（技能系统）子模块 |
| 包路径 | [`com.fomdev.flamerpg.modules.frpg_skill`](./src/main/java/com/fomdev/flamerpg/modules/frpg_skill) |
| 主类 | [`com.fomdev.flamerpg.modules.frpg_skill.FRPGSkill`](./src/main/java/com/fomdev/flamerpg/modules/frpg_skill/FRPGSkill.java) |
| Mod ID | `frpg_skill` |
| 模组名称 | `FlameRPG: Skill` |

---

## ▍四、技术规格 / TECHNICAL SPECIFICATION

| 项目 | 值 |
| :--- | :--- |
| Minecraft | `1.21.1` |
| 模组加载器 | NeoForge `21.1.252`（`javafml`） |
| Java 工具链 | Java `21` |
| 构建插件 | ModDevGradle `2.0.148` |
| 映射 | Parchment `2024.11.17` |
| 模组版本 | `1.0.0` |
| 授权 | All Rights Reserved（保留所有权利） |

### 构建

```bash
./gradlew build
```

产物输出至 `build/libs/`。

---

<div align="center">

**FLAME RPG** · `flamerpg` + `frpg_skill`

*Adventure & RPG — 一体化交付*

</div>
