# PlutoMC

> **更新于 2023/8/26：**
>
> 此项目为 PlutoMC 二周目时期遗留的开发中内容，并且已经被废弃。
>
> 请移步到 [此仓库](https://github.com/PlutoProject/pluto-plugins) 查看我们正在为星社开发的内容。

[![CodeFactor](https://www.codefactor.io/repository/github/plutosmp/plutomc/badge)](https://www.codefactor.io/repository/github/plutosmp/plutomc)
![GitHub language count](https://img.shields.io/github/languages/count/plutosmp/PlutoMC)
![GitHub](https://img.shields.io/github/license/plutosmp/PlutoMC)
![](https://img.shields.io/github/last-commit/nostalgic853/PlutoMC-Engine?logo=artstation&flat&color=9266CC)

⚡ PlutoMC Season 2 自研内容。

本仓库包含了所有由我们自主为 Season 2 开发的内容。

# 贡献

如果您是一位了解 Java 与 Kotlin 编程的玩家，您可以在直接 fork 本仓库并做出您想要的修改后提交 Pull request。

我们推荐您查看 服务器贡献指南 以更好的了解如何为本项目做贡献。

**温馨提示：在进行提交时，请根据 [此处](https://zhuanlan.zhihu.com/p/34223150) 展示的提交信息书写方法来书写提交信息。**

# 分支规则

- `dev`: 开发分支，包含不稳定的代码。
- `prod`: 生产分支，所有已经确认稳定并且可在生产环境中使用的内容会被提交到此处。

# 模块说明

- `framework-shared`: 开发框架通用部分，大部分内容为抽象接口类。
- `framework-bukkit`: 开发框架 Bukkit 专用部分。
- `framework-velocity`: 开发框架 velocity 专用部分。
- `proxy-bootstrap`: 代理端服务器插件。通过框架中的模块功能将需要的模块打入并加载。
- `survival-bootstrap`: 主生存服务器插件。通过框架中的模块功能将需要的模块打入并加载。
- `resource-bootstrap`: 资源世界服务器插件。通过框架中的模块功能将需要的模块打入并加载。
- `event-bootstrap`: 活动中心服务器插件。通过框架中的模块功能将需要的模块打入并加载。
- `module-*`: 各类插件模块。
- `library-*`: 一些没有发布到 Maven 仓库的依赖库。
- `common-*`: 一些除了插件平台开发还能用到的辅助工具。
- `bot-qq`: QQ 机器人。
- `bot-kook`: KOOK 机器人。

# 开发语言

本项目目前使用 Java 语言进行开发。

# 许可

PlutoMC 在 [GNU General Public License v3](https://www.gnu.org/licenses/gpl-3.0.en.html) 下许可。

---

Copyright © 2022 PlutoMC Team & all contributors. All rights reserved.
