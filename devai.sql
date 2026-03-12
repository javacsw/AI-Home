/*
 Navicat Premium Dump SQL

 Source Server         : mysql5.7
 Source Server Type    : MySQL
 Source Server Version : 50744 (5.7.44-log)
 Source Host           : localhost:3306
 Source Schema         : devai

 Target Server Type    : MySQL
 Target Server Version : 50744 (5.7.44-log)
 File Encoding         : 65001

 Date: 31/03/2025 23:29:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_blogs
-- ----------------------------
DROP TABLE IF EXISTS `tb_blogs`;
CREATE TABLE `tb_blogs`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf16le COLLATE utf16le_general_ci NOT NULL,
  `authorId` int(11) NOT NULL,
  `updateDate` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `watches` int(11) NULL DEFAULT 0,
  `favourites` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0',
  `likes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0',
  `isPublic` tinyint(4) NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blogs
-- ----------------------------
INSERT INTO `tb_blogs` VALUES (2, '智绘家园', 2, '2025-03-20 20:42:19', 0, '0', '0', NULL, '智绘家园即利用人工智能技术提升测绘效率和精度，正在革新传统测绘行业，其优势主要体现在以下几个方面：\n\n1. 效率提升:\n\n自动化处理: AI 可以自动完成大量重复性工作，例如图像识别、点云分类、地物提取等，大幅减少人工操作，提高工作效率。\n\n实时处理: AI 算法可以实时处理和分析海量测绘数据，例如无人机航拍影像、激光雷达点云等，快速生成测绘成果，满足时效性要求高的应用场景。\n\n并行计算: AI 可以利用分布式计算和 GPU 加速等技术，实现大规模数据的并行处理，进一步提高效率。\n\n2. 精度提高:\n\n智能识别: AI 算法可以自动识别和分类地物，例如建筑物、道路、植被等，减少人为误差，提高测绘精度。\n\n数据融合: AI 可以融合多源异构数据，例如影像、点云、GIS 数据等，进行综合分析，提高数据精度和可靠性。\n\n持续学习: AI 模型可以通过不断学习新的数据，优化算法，提高识别精度和泛化能力。\n\n3. 成本降低:\n\n减少人力成本: AI 自动化处理可以减少对人工的依赖，降低人力成本。\n\n提高资源利用率: AI 可以优化资源配置，例如无人机飞行路径规划、数据处理任务分配等，提高资源利用率，降低成本。\n\n降低设备成本: AI 算法可以提升数据处理效率，降低对硬件设备的性能要求，从而降低设备成本。\n\n4. 应用拓展:\n\n新型测绘产品: AI 可以生成传统测绘手段难以获取的新型测绘产品，例如三维实景模型、高精度地图、数字孪生城市等。\n\n智能化应用: AI 可以赋能测绘数据的智能化应用，例如自动驾驶、智慧城市、灾害监测等，拓展测绘行业的应用领域。\n\n个性化服务: AI 可以根据用户需求，提供个性化的测绘服务，例如定制化地图、精准导航等。\n\n总而言之，AI 测绘正在推动测绘行业向自动化、智能化、实时化方向发展，其优势将不断显现，为各行各业带来更高效、更精准、更便捷的测绘服务。\n\n需要注意的是，AI 测绘也面临着数据安全、算法偏见、伦理道德等挑战，需要行业共同努力，推动 AI 测绘健康可持续发展。\n\n');
INSERT INTO `tb_blogs` VALUES (5, '房屋设计建议：打造舒适与功能兼具的理想家居', 1, '2025-03-24 21:14:06', 0, '0', '0', NULL, '房屋设计不仅仅是空间布局的规划，更是生活方式和个性的体现。一个优秀的房屋设计应该兼顾美观、实用性和舒适性，同时满足居住者的个性化需求。以下是一些关于房屋设计的建议，帮助您打造理想的家居环境。\n\n一、明确需求与风格定位\n在设计房屋之前，首先要明确居住者的需求和偏好。以下是一些关键问题：\n\n家庭成员结构：是否有老人、小孩或宠物？不同年龄段的需求不同。\n\n生活方式：是否需要开放式厨房、书房或娱乐空间？\n\n风格偏好：喜欢现代简约、北欧风、工业风还是传统中式风格？\n\n预算范围：根据预算合理规划设计和材料选择。\n\n建议：可以通过浏览设计案例、参考家居杂志或使用设计软件（如SketchUp、AutoCAD）来明确自己的风格偏好。\n\n二、合理规划空间布局\n空间布局是房屋设计的核心，合理的布局可以最大化利用空间，提升居住舒适度。\n\n功能分区明确：\n\n将房屋划分为公共区域（客厅、餐厅、厨房）和私密区域（卧室、书房）。\n\n动静分离：将安静的区域（如卧室）与活动频繁的区域（如客厅）分开，避免相互干扰。\n\n开放式设计：\n\n开放式厨房和客厅可以增加空间的通透感，适合小户型或喜欢社交的家庭。\n\n使用玻璃隔断或半墙设计，既能划分空间，又不会显得压抑。\n\n灵活空间利用：\n\n多功能房间：例如书房可以兼顾客房，儿童房可以预留游戏区。\n\n利用垂直空间：增加储物柜、书架或 loft 设计，提升空间利用率。\n\n三、注重采光与通风\n良好的采光和通风是舒适居住环境的基础。\n\n最大化自然光：\n\n尽量保留大窗户或落地窗，增加自然光的进入。\n\n使用浅色墙面和反光材质（如镜面、玻璃）来增强光线反射。\n\n通风设计：\n\n确保房屋有良好的空气流通，避免潮湿和闷热。\n\n在厨房和卫生间安装排气扇，保持空气清新。\n\n窗帘与遮光：\n\n选择透光性好的窗帘，既能保证隐私，又不影响采光。\n\n在卧室使用遮光窗帘，提升睡眠质量。\n\n四、色彩与材质的选择\n色彩和材质的选择直接影响房屋的整体氛围和视觉效果。\n\n色彩搭配：\n\n主色调建议选择中性色（如白色、灰色、米色），易于搭配且耐看。\n\n局部使用亮色（如蓝色、绿色、黄色）作为点缀，增加活力。\n\n小户型建议使用浅色系，视觉上显得更宽敞。\n\n材质选择：\n\n地面材质：木地板适合卧室和客厅，瓷砖适合厨房和卫生间。\n\n墙面材质：乳胶漆经济实用，墙纸或艺术涂料可以增加质感。\n\n家具材质：实木家具耐用且环保，布艺家具舒适温馨。\n\n五、智能化与节能设计\n现代房屋设计越来越注重智能化和节能环保。\n\n智能化家居：\n\n安装智能门锁、智能灯光系统和智能温控设备，提升生活便利性。\n\n使用智能家电（如扫地机器人、智能音箱）减轻家务负担。\n\n节能设计：\n\n选择节能灯具（如LED灯）和节能电器，降低能耗。\n\n安装双层玻璃窗或保温材料，减少热量流失。\n\n考虑太阳能热水器或光伏发电系统，充分利用可再生能源。\n\n六、储物空间设计\n充足的储物空间是保持房屋整洁的关键。\n\n嵌入式设计：\n\n利用墙面设计嵌入式衣柜或储物柜，节省空间。\n\n在床下、楼梯下等角落设计隐藏式储物空间。\n\n分区收纳：\n\n根据不同区域的功能设计收纳方案，例如厨房的餐具收纳、客厅的书籍收纳。\n\n使用收纳盒、挂钩等小工具，让物品分类存放。\n\n七、个性化与艺术感\n房屋设计应体现居住者的个性和审美。\n\n装饰品与艺术品：\n\n挂画、雕塑或绿植可以为空间增添艺术气息。\n\n选择与整体风格匹配的装饰品，避免过于杂乱。\n\n灯光设计：\n\n使用多层次灯光（主灯、射灯、台灯）营造不同的氛围。\n\n在重点区域（如餐桌、书桌）使用聚焦灯光，提升实用性。\n\n个性化细节：\n\n在墙面设计照片墙或手绘墙，展示个人回忆或创意。\n\n选择独特的家具或定制家具，体现个人品味。\n\n八、环保与健康\n房屋设计应注重环保和健康，为居住者提供一个安全舒适的环境。\n\n环保材料：\n\n选择低甲醛的板材、环保涂料和天然石材，减少室内污染。\n\n避免使用含有有害物质的装修材料。\n\n空气质量：\n\n安装空气净化器或新风系统，保持室内空气清新。\n\n在室内放置绿植（如吊兰、绿萝），吸收有害气体。\n\n总结\n房屋设计是一个系统的工程，需要综合考虑功能、美观和舒适性。通过合理规划空间、注重细节设计、融入智能化元素，您可以打造一个既实用又充满个性的理想家居。希望以上建议能为您的房屋设计提供灵感和帮助！\n\n如果您有具体的需求或问题，欢迎进一步交流！');

-- ----------------------------
-- Table structure for tb_users
-- ----------------------------
DROP TABLE IF EXISTS `tb_users`;
CREATE TABLE `tb_users`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mail` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `avator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `bio` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_users
-- ----------------------------
INSERT INTO `tb_users` VALUES (1, '456', '456', '123456789@qq.com', 'https://imgs.flyinsky.wiki/file/AgACAgUAAyEGAASHZUXDAAM_Z9Atxlvvn2ThFt4OguhgakN_BGMAArK_MRt5-4BWUifCG-kfwGIBAAMCAAN5AAM2BA.png', '一个热爱编程的java初学者。', '小红');
INSERT INTO `tb_users` VALUES (2, '123', '123', '123456789@qq.com', 'https://imgs.flyinsky.wiki/file/AgACAgUAAyEGAASHZUXDAAM-Z9AtgTHo1035tsKClfDNfAv1FZ8AArG_MRt5-4BWoJM9DOQsKUoBAAMCAAN5AAM2BA.png', '书山有路勤为径，学海无涯苦作舟', '小唐');

SET FOREIGN_KEY_CHECKS = 1;
