# ChildrenWorld - 儿童识字乐园

聚焦于Android 手机/平板端，适用于3~10岁儿童的识字（识字 + 发音 + 简单语音识读反馈 + 游戏化激励）软件。

## 功能特点

### 1. 识字学习 (Character Learning)
- 提供40个常用汉字，适合儿童学习
- 大字体显示，方便儿童阅读
- 包含拼音标注，帮助发音学习

### 2. 发音功能 (Pronunciation)
- 集成文本转语音 (TTS) 技术
- 支持中文语音播放
- "听发音"按钮让孩子反复聆听标准发音

### 3. 语音识读反馈 (Speech Recognition)
- 使用 Android 语音识别 API
- "我来读"功能让孩子练习发音
- 实时反馈：正确显示"太棒了！⭐"，鼓励继续学习

### 4. 游戏化激励 (Gamification)
- 星星奖励系统：学习和答对问题获得星星
- 进度追踪：记录已学汉字数量
- 趣味游戏：找汉字游戏，强化学习效果
- 得分系统：激励孩子持续学习

### 5. 儿童友好设计
- 明亮色彩搭配 (蓝、绿、黄、橙等)
- 大按钮设计，便于小手指操作
- 简洁直观的界面
- 竖屏锁定，防止意外旋转

## 技术架构

- **开发语言**: Kotlin
- **最低 Android 版本**: Android 7.0 (API 24)
- **目标 Android 版本**: Android 14 (API 34)
- **主要技术**:
  - Android Text-to-Speech (TTS)
  - Android SpeechRecognizer
  - Material Design Components
  - SharedPreferences for progress tracking

## 项目结构

```
app/
├── src/main/
│   ├── java/com/childrenworld/literacy/
│   │   ├── CharacterRepository.kt      # 汉字数据仓库
│   │   ├── ProgressManager.kt          # 进度管理
│   │   ├── MainActivity.kt             # 主界面
│   │   ├── LearningActivity.kt         # 学习界面
│   │   └── GameActivity.kt             # 游戏界面
│   ├── res/
│   │   ├── layout/                     # 界面布局
│   │   ├── values/                     # 字符串、颜色、主题
│   │   └── ...
│   └── AndroidManifest.xml
└── build.gradle

```

## 如何构建

1. 安装 Android Studio (推荐最新版本)
2. 克隆仓库:
   ```bash
   git clone https://github.com/CjieSun/ChildrenWorld.git
   ```
3. 在 Android Studio 中打开项目
4. 等待 Gradle 同步完成
5. 连接 Android 设备或启动模拟器
6. 点击 Run 按钮构建并安装应用

## 权限说明

应用需要以下权限：
- `RECORD_AUDIO`: 用于语音识别功能，让孩子练习发音
- `INTERNET`: 用于语音服务（如果需要）

## 使用指南

1. **开始学习**: 点击"开始学习"按钮进入学习模式
2. **听发音**: 点击"听发音"按钮，应用会朗读当前汉字
3. **我来读**: 点击"我来读"按钮，对着麦克风说出汉字
4. **导航**: 使用"上一个"和"下一个"按钮浏览不同汉字
5. **玩游戏**: 点击"趣味游戏"按钮进入找汉字游戏

## 学习内容

包含 40 个基础汉字，涵盖：
- 基本数字 (一、二、三...十)
- 自然元素 (日、月、水、火、山、石...)
- 常用字词 (人、口、手、大、小、上、下...)
- 人称代词 (我、你、他、她)
- 常用动词和形容词 (好、学、爱...)

## 后续改进方向

- [ ] 添加更多汉字和学习关卡
- [ ] 增加动画效果，提升趣味性
- [ ] 添加汉字书写练习功能
- [ ] 支持家长监控和学习报告
- [ ] 添加更多游戏类型
- [ ] 支持离线语音包
- [ ] 增加成就系统和徽章

## 贡献

欢迎提交 Issue 和 Pull Request！

## 许可证

MIT License
