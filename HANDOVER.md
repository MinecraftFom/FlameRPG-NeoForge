# Flame RPG 交接文档 / HANDOVER

> 文档版本：v1 · 2026-10-17
> 适用仓库：`FlameRPG-NeoForge`（NeoForge 1.21.1 分支）
> 读者对象：接手本项目的开发者、构建打包者、服务器运维

---

## 一、这是什么

Flame RPG（简称 FRPG）是一个面向 **Minecraft 多人服务器** 的 **MMORPG 玩法模组**，
目标是让玩家在一体化的体验里同时获得「冒险」与「角色扮演」两条主线。

模组采用 **模块化装配结构**，由多个子模块协同构成：

| 模块 | Mod ID | 显示名 | 职责 |
| :--- | :--- | :--- | :--- |
| 主模组 | `flamerpg` | FlameRPG: Core | 父模组，负责全局注册与核心装配 |
| 子模块 | `frpg_skill` | FlameRPG: Skill | 技能系统与技能树 |

---

## 二、五分钟接手

### 2.1 环境要求

| 项目 | 要求 |
| :--- | :--- |
| JDK | **21**（构建与运行都必须是 21） |
| 网络 | 首次构建需联网下载 Gradle 发行版与 NeoForge 依赖 |
| 磁盘 | 首次构建建议预留 1.5 GB 以上（`~/.gradle` 缓存） |
| Gradle | 无需自行安装，用仓库自带 wrapper（9.2.1） |

### 2.2 拉代码并构建

```bash
git clone https://github.com/zzy89216-gif/FlameRPG-NeoForge_zzy.git
cd FlameRPG-NeoForge_zzy

export JAVA_HOME=/path/to/jdk-21
./gradlew build
```

产物位于 `build/libs/`。

### 2.3 常用 Gradle 任务

| 任务 | 用途 |
| :--- | :--- |
| `./gradlew build` | 编译 + 打包，产物在 `build/libs/` |
| `./gradlew runClient` | 启动带模组的客户端 |
| `./gradlew runServer` | 启动带模组的服务端（`--nogui`） |
| `./gradlew runData` | 运行数据生成，输出到 `src/generated/resources/` |
| `./gradlew clean` | 清理构建产物 |

---

## 三、技术栈与版本矩阵

| 项目 | 版本 | 定义位置 |
| :--- | :--- | :--- |
| Minecraft | `1.21.1` | `gradle.properties` → `minecraft_version` |
| NeoForge | `21.1.252` | `gradle.properties` → `neo_version` |
| Java | `21` | `build.gradle` → `java.toolchain` |
| Gradle | `9.2.1` | `gradle/wrapper/gradle-wrapper.properties` |
| 构建插件 | ModDevGradle `2.0.148` | `build.gradle` → `plugins` |
| 映射 | Parchment `2024.11.17` | `gradle.properties` → `parchment_mappings_version` |
| 模组版本 | `1.0.0` | `gradle.properties` → `mod_version` |
| 授权 | All Rights Reserved | `gradle.properties` → `mod_license` |

**版本升级时的联动点**：改 `gradle.properties` 里的版本号即可，
`src/main/templates/META-INF/neoforge.mods.toml` 中的 `${...}` 占位符
由 `generateModMetadata` 任务在打包时统一替换，**不要**手工改模板里的占位符。

---

## 四、仓库与分支

| 远程 | 地址 | 说明 |
| :--- | :--- | :--- |
| `origin` | `zzy89216-gif/FlameRPG-NeoForge_zzy` | 当前开发仓库 |
| `upstream` | `MinecraftFom/FlameRPG-NeoForge` | 上游原仓库，用于跟进官方更新 |

**同步上游：**

```bash
git fetch upstream
git merge upstream/master      # 或 git rebase upstream/master
```

**分支约定：** 目前只有 `master`。建议后续按 `feat/*`、`fix/*`、`chore/*` 开分支，
经 PR 合并进 `master`，由 GitHub Actions 自动构建校验。

---

## 五、代码结构导览

```
src/main/java/com/fomdev/flamerpg/
├── init/FlameRPG.java                    主模组入口（@Mod("flamerpg")）
├── core/
│   ├── AbstractEventHook.java            事件钩子基类：靠反射定位 @EventHookStatusStorer 字段
│   └── EventHookStatusStorer.java        标记注解，用于标记"初始化状态"字段
├── event/EventHooks.java                 全局钩子单例表
├── registry/
│   ├── FRPGRegistries.java               自定义注册表（如 skill_page）
│   └── data/FRPGAttachments.java         玩家数据附件（Data Attachment）注册
├── util/
│   ├── Mth.java                          数学工具（偶数/奇数/向上取整）
│   ├── Util.java                         通用工具门面（统一转发至 Mth / RGBColor）
│   └── RGBColor.java                     RGBA 颜色值对象（替代 java.awt.Color）
├── mixin/package-info.java               Mixin 注入点包（当前为空，保证配置声明的包存在）
└── modules/frpg_skill/                   技能子模块
    ├── FRPGSkill.java                    子模块入口（@Mod("frpg_skill")）
    ├── event/FRPGSkillEventHooks.java    技能事件钩子
    ├── event/FillSkillTreeEvent.java     填充技能树事件（可被其他模组监听）
    ├── skill/Skill.java                  技能数据结构（依赖、显示、行为）
    ├── skill/SkillPage.java              技能页定义
    └── storage/SkillDataHolder.java      玩家技能数据（点数、已解锁），带 Codec

src/main/templates/META-INF/neoforge.mods.toml   模组元数据模板（打包时展开）
src/main/resources/                               资源：mixins 配置、语言文件、贴图
src/generated/resources/                          数据生成输出（如需保留请提交）
```

**关键设计说明：**

- **技能树是开放式的。** `FillSkillTreeEvent` 交给 `NeoForge.EVENT_BUS` 分发，
  任何模组都可以监听该事件并 `addSkill(...)`，从而往技能树里挂载自己的技能。
  这决定了新增技能**不一定**要改本模组代码。
- **玩家数据用 Data Attachment。** `SkillDataHolder` 挂在玩家身上，
  经 `FRPGAttachments.DC_SKILL` 存取，自带 `Codec`（存档）与 `StreamCodec`（联机同步）。
- **`AbstractEventHook` 的初始化保护。** 基类构造时通过反射找到子类里标注了
  `@EventHookStatusStorer` 的静态 `boolean` 字段，若已为 `true` 则抛异常，
  用于防止钩子被重复实例化。

---

## 六、当前状态

### 6.1 已完成

- 主模组与技能子模块的骨架、注册表与数据附件注册链路
- 技能数据结构、技能页、技能树事件
- 玩家技能数据的序列化与网络同步
- 中文规格文档（`README.md`）
- 构建工程化：`.gitignore`、`LICENSE`、GitHub Actions、上游 remote
- **构建已验证通过**：`./gradlew build` 在 JDK 21 / Ubuntu 24.04 下 `BUILD SUCCESSFUL`，
  产物 `build/libs/flamerpg-1.0.0.jar` 内含模组元数据、语言文件、贴图与 mixin 配置

### 6.2 已知限制 / 待办

| 优先级 | 事项 | 说明 |
| :--- | :--- | :--- |
| 高 | 技能树 UI 尚未实现 | `Skill.java` 只定义了显示属性与形状，没有对应的界面渲染与交互 |
| 高 | 贴图为占位图 | `assets/flamerpg/textures/gui/` 下 6 张图为程序生成的占位素材，需美术替换 |
| 中 | 技能内容为空 | 没有内置技能数据，全靠外部监听 `FillSkillTreeEvent` 注入 |
| 中 | 无游戏内测试 | 未接入 GameTest，建议为注册表与数据附件补最小测试 |
| 中 | 未发布 | 没有 Modrinth / CurseForge 页面，没有打 tag 发版 |
| 低 | `Util` 里的工具方法偏少 | 颜色取反等属于 UI 辅助，后续可能随 UI 实现扩充 |

---

## 七、发布流程

1. 确认 `gradle.properties` 中 `mod_version` 已递增；
2. 更新 `CHANGELOG.md`；
3. 本地执行 `./gradlew build`，确认 `build/libs/flamerpg-<version>.jar` 生成；
4. 打 tag 并推送：`git tag v<version> && git push origin v<version>`；
5. 在 GitHub Release 中附上该 jar，并同步上传至 Modrinth / CurseForge。

---

## 八、授权与合规

本项目为 **All Rights Reserved（保留所有权利）**，详见仓库根目录的 `LICENSE`。
未经版权持有人书面许可，不得再分发、修改或用于商业用途（含付费服务器与付费整合包）。

**凭据安全：** 任何 GitHub Token、密码、密钥都**不得**写进仓库、提交信息或代码注释。
推送时建议使用 SSH 或 `git credential` 管理器，不要用明文 URL。

---

## 九、常见故障排查

| 现象 | 原因 | 处理 |
| :--- | :--- | :--- |
| `./gradlew: Permission denied` | 克隆后 wrapper 缺少可执行权限 | `chmod +x gradlew` |
| `trustAnchors parameter must be non-empty` | JDK 的 `cacerts` 为空（Linux 下常见） | `update-ca-certificates -f` 后执行 `ca-certificates-java.postinst configure` |
| 启动报找不到 `flamerpg.mixins.json` | mixin 配置文件缺失 | 确认 `src/main/resources/flamerpg.mixins.json` 存在 |
| 技能树图标显示为白块 | 贴图缺失或路径错误 | 检查 `assets/flamerpg/textures/gui/` |
| 首次构建极慢 | 需下载 Gradle 发行版与全部依赖 | 耐心等待，或配置国内镜像 |
