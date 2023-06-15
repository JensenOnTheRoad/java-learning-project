package binarytree;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 二叉树
 *
 * @author jensen_deng
 */
@Slf4j
public class TestBinaryTree {

  // region test

  private TreeNode<Integer> root;

  @BeforeEach
  void setup() {
    root = new TreeNode<>(1);

    root.left = new TreeNode<>(2);
    root.right = new TreeNode<>(3);

    root.left.left = new TreeNode<>(4);
    root.left.right = new TreeNode<>(5);

    root.right.left = new TreeNode<>(6);
    root.right.right = new TreeNode<>(7);
  }

  @Test
  @DisplayName("先序遍历")
  void test_pre_order_traversal() {
    preOrderTraversal(root);
  }

  @Test
  @DisplayName("中序遍历")
  void test_in_order_traversal() {
    inOrderTraversal(root);
  }

  @Test
  @DisplayName("后序遍历")
  void test_post_order_traversal() {
    posOrderTraversal(root);
  }

  // endregion

  // region
  /**
   * 二叉树先序遍历
   *
   * @param treeNode rootTreeNode
   */
  private void preOrderTraversal(TreeNode<?> treeNode) {
    if (treeNode != null) {
      log.info("data: {}", treeNode.getData());
      preOrderTraversal(treeNode.getLeft());
      preOrderTraversal(treeNode.getRight());
    }
  }

  public void inOrderTraversal(TreeNode<?> treeNode) {
    if (treeNode != null) {
      preOrderTraversal(treeNode.getLeft());
      log.info("data: {}", treeNode.getData());
      preOrderTraversal(treeNode.getRight());
    }
  }

  public void posOrderTraversal(TreeNode<?> treeNode) {
    if (treeNode != null) {
      preOrderTraversal(treeNode.getLeft());
      preOrderTraversal(treeNode.getRight());
      log.info("data: {}", treeNode.getData());
    }
  }

  // endregion

  // region tree define
  @Data
  private static class TreeNode<T> {

    private T data;

    private TreeNode<T> left;

    private TreeNode<T> right;

    public TreeNode(T data) {
      this.data = data;
    }
  }
  // endregion
}
