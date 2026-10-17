# 更新日志 / CHANGELOG

本项目的全部重要变更均记录于此。
格式参考 [Keep a Changelog](https://keepachangelog.com/zh-CN/1.1.0/)，
版本号遵循 [语义化版本](https://semver.org/lang/zh-CN/)。

---

## [未发布] / Unreleased

### 新增

- **模组资源结构**：补齐 `src/main/resources/` 目录（此前完全缺失）
  - `flamerpg.mixins.json`：与 `neoforge.mods.toml` 中 `[[mixins]]` 的声明对应
  - `assets/flamerpg/lang/en_us.json`、`zh_cn.json`：技能树界面文案（为后续 UI 预留）
  - `assets/flamerpg/textures/gui/`：`SkillShapes` 引用的 6 张形状贴图（占位素材）
  - `com.fomdev.flamerpg.mixin` 包：保证 Mixin 配置声明的包真实存在
- **`.gitignore`**：排除 `build/`、`.gradle/`、`run/`、IDE 目录与构建缓存
- **`LICENSE`**：与 `mod_license=All Rights Reserved` 对应的授权声明
- **`.github/workflows/build.yml`**：push / PR 自动构建并上传 jar 产物
- **`HANDOVER.md`**：交接文档（环境、结构、流程、已知问题、排障）
- **`CHANGELOG.md`**：本文件

### 变更

- **移除 `java.awt.Color` 依赖**：颜色类型改为项目自有的 `RGBColor`（RGBA 值对象），
  避免在裁剪运行时/headless 服务端上依赖 `java.desktop` 模块。
  - `Skill.SkillDisplayProperties#color` / `#border`：`Optional<Color>` → `Optional<RGBColor>`
  - `Util.opposite(Color)` → `Util.opposite(RGBColor)`
- **`Util` 收敛为门面**：数值逻辑统一由 `Mth` 实现，`Util` 只做转发，消除重复代码
- **`AbstractEventHook`**：状态字段查找改为沿继承链向上遍历，并校验字段必须是
  `static boolean`；所有异常改为携带具体类名/字段名的 `IllegalStateException`
- **README**：修正 Mod ID（`flame_rpg` → `flamerpg`），补充文档导航与构建徽章
- **README 拆分为双语**：`README.md` 改为英文主文档，中文版独立为 `README.zh-CN.md`；
  两份文档顶部各有一个独立成行的语言切换按钮（不并入技术徽章行）

### 修复

- **`gradlew` 缺少可执行权限**（仓库内 mode 为 `100644`），Linux / macOS 下
  直接执行会报 `Permission denied`
- **`Mth.round`** 对负数取整结果错误（原实现固定 `+1`，实为向零取整）；
  改为正确的向上取整，并对 `dr == 0` 抛出明确的 `ArithmeticException`
- **`AbstractEventHook` 子类继承时失效**：原实现只扫描 `getClass()` 自身的字段，
  钩子类一旦继承另一个钩子就会抛 "Invalid EventHook class"

### 说明

- `assets/flamerpg/lang/` 中的键当前**尚未被代码引用**，属于技能树界面的预留文案；
  待 UI 实现后接入。
- `assets/flamerpg/textures/gui/` 中的贴图为程序生成的几何占位图（512×512），
  需由美术资源替换。

### 未完成 / 未验证

- **模组尚未真正启动过。** `./gradlew runServer` 需先下载约 3800 个客户端资源，
  本次未跑完即中止，因此 mixin 配置、语言文件、贴图在运行期的加载情况**未经实测**。
- **技能树 UI 未实现**，没有任何界面/渲染代码。
- **无测试用例**，`./gradlew test` 恒为 `NO-SOURCE`。
- **GitHub Actions 未实际运行**，工作流推送后才有首次执行记录。
- **未修**：`SkillDataHolder` 的 record 内持有可变 `ArrayList`，`unlock()` 原地修改
  并返回 `this`，与 record 的 `equals/hashCode` 语义不一致。
- **未改**：`FillSkillTreeEvent#isFor` 的比较对象可疑，需作者确认原始意图。

---

## [1.0.0] — 开发基线 / Baseline

> 状态：开发中，尚未对外发版。对应上游 `MinecraftFom/FlameRPG-NeoForge` 提交 `7542843`。

### 新增

- 主模组 `flamerpg`（FlameRPG: Core）骨架与 `@Mod` 入口
- 自定义注册表 `FRPGRegistries`（`skill_page`）
- 玩家数据附件 `FRPGAttachments.DC_SKILL`，基于 NeoForge Data Attachment
- 技能子模块 `frpg_skill`（FlameRPG: Skill）
  - `Skill` / `SkillPage` 数据结构
  - `FillSkillTreeEvent`：允许外部模组向技能树注入技能
  - `SkillDataHolder`：技能点与已解锁技能的存档 Codec 与网络 StreamCodec
- 事件钩子体系 `AbstractEventHook` + `@EventHookStatusStorer`
