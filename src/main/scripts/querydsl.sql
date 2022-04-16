CREATE TABLE `teacher`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

INSERT INTO `teacher` VALUES (1, '张老师', 1650118971370);
INSERT INTO `teacher` VALUES (2, '王老师', 1650118971370);
INSERT INTO `teacher` VALUES (3, '李老师', 1650118971370);

CREATE TABLE `student`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `score` int NULL DEFAULT NULL,
  `teacher_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

INSERT INTO `student` VALUES (1, '张三', 80, 1);
INSERT INTO `student` VALUES (2, '李四', 90, 1);
INSERT INTO `student` VALUES (3, '王五', 100, 1);
INSERT INTO `student` VALUES (4, '赵六', 70, 2);