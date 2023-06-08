import java.util.LinkedList;
import java.util.Stack;

////////////////FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
//Title:    RedBlackTree P2W3
//Course:   CS 400 Spring 2023
//
//Author:   Kyran Sinha
//Email:    ksinha22@wisc.edu
//Lecturer: Gary Dahl
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
//Persons:         none
//Online Sources:  none
//
///////////////////////////////////////////////////////////////////////////////
/**
 * Red-Black Tree implementation with a Node inner class for representing the
 * nodes of the tree. Currently, this implements a Binary Search Tree that we
 * will turn into a red black tree by modifying the insert functionality. In
 * this activity, we will start with implementing rotations for the binary
 * search tree insert algorithm. I will continue ot use this for the porject
 * utilizing the SortedCollectionInterface as decided by the team. Extended
 * Functionality- Traversal, New FindSuccessor and implementation of Remove();
 */
public class RedBlackTreeAE<T extends Comparable<T>> implements RedBlackTreeInterface<T> {

  /**
   * This class represents a node holding a single value within a binary tree.
   */
  protected static class Node<T> {
    public T data;
    // The context array stores the context of the node in the tree:
    // - context[0] is the parent reference of the node,
    // - context[1] is the left child reference of the node,
    // - context[2] is the right child reference of the node.
    // The @SupressWarning("unchecked") annotation is used to supress an unchecked
    // cast warning. Java only allows us to instantiate arrays without generic
    // type parameters, so we use this cast here to avoid future casts of the
    // node type's data field.
    @SuppressWarnings("unchecked")
    public Node<T>[] context = (Node<T>[]) new Node[3];
    public int blackHeight;

    public Node(T data) {
      this.blackHeight = 0;
      this.data = data;
    }

    /**
     * @return true when this node has a parent and is the right child of that
     *         parent, otherwise return false
     */
    public boolean isRightChild() {
      return context[0] != null && context[0].context[2] == this;
    }

  }

  protected Node<T> root; // reference to root node of tree, null when empty
  protected int size = 0; // the number of values in the tree

  /**
   * Enforces the properties of a red-black tree after an insertion operation has
   * been performed. It takes as input a Node object representing the newly
   * inserted node, and updates the tree to ensure that it satisfies the red-black
   * tree properties
   * 
   * @param newRed the newly inserted node
   */
  protected void enforceRBTreePropertiesAfterInsert(Node<T> newNode)
  {
    if(newNode == root)
    {
      newNode.blackHeight = 1;
      return;
    }
    Node<T> parent = newNode.context[0];
    
    if(parent == null)
    {
      return;
    }
    
    Node<T> grandParent = newNode.context[0].context[0];
    
    if(grandParent == null)
    {
      return;
    }
    
    while(newNode != root && newNode.blackHeight == 0 && parent.blackHeight == 0)
    {
      //Case 1: parent is the left child of the grandparent
      if(grandParent.context[1] != null && grandParent.context[1] == parent)
      {
        //Case 1a: parents sibling is black or null
        if(grandParent.context[2] == null || grandParent.context[2].blackHeight == 1)
        {
          if(parent.context[2] == newNode)
          {
            rotate(parent.context[2], parent);
            Node<T> temp = newNode;
            newNode = parent;
            parent = temp;
          }
          rotate(parent, grandParent);
          int temp = parent.blackHeight;
          parent.blackHeight = grandParent.blackHeight;
          grandParent.blackHeight = temp;
          enforceRBTreePropertiesAfterInsert(parent);
        }
        //case 1b: parents sibling is red
        else
        {
          grandParent.blackHeight = 0;
          parent.blackHeight = 1;
          grandParent.context[2].blackHeight = 1;
          enforceRBTreePropertiesAfterInsert(grandParent);
        }
      }
      //Case 2: parent is the right child of the grandparent
      else
      {
        //case 2a: parents sibling is black
        if(grandParent.context[1] == null ||  grandParent.context[1].blackHeight == 1)
        {
          if(parent.context[1] == newNode)
          {
            rotate(parent.context[1], parent);
            Node<T> temp = newNode;
            newNode = parent;
            parent = temp;
          }
          rotate(parent, grandParent);
          int temp = parent.blackHeight;
          parent.blackHeight = grandParent.blackHeight;
          grandParent.blackHeight = temp;
          enforceRBTreePropertiesAfterInsert(parent);
        }
        //case 2b: parents sibling is red
        else
        {
          grandParent.blackHeight = 0;
          parent.blackHeight = 1;
          grandParent.context[1].blackHeight = 1;
          enforceRBTreePropertiesAfterInsert(grandParent);
        }
      }
    }
    root.blackHeight = 1;
  }
  /**
   * Performs a naive insertion into a binary search tree: adding the input data
   * value to a new node in a leaf position within the tree. After this insertion,
   * no attempt is made to restructure or balance the tree. This tree will not
   * hold null references, nor duplicate data values.
   * 
   * @param data to be added into this binary search tree
   * @return true if the value was inserted, false if not
   * @throws NullPointerException     when the provided data argument is null
   * @throws IllegalArgumentException when data is already contained in the tree
   */

  public boolean insert(T data) throws NullPointerException, IllegalArgumentException {
    // null references cannot be stored within this tree
    if (data == null)
      throw new NullPointerException("This RedBlackTree cannot store null references.");

    Node<T> newNode = new Node<>(data);
    if (this.root == null) {
      // add first node to an empty tree
      root = newNode;
      size++;
      return true;
    } else {
      // insert into subtree
      Node<T> current = this.root;
      while (true) {
        int compare = ((newNode.data).compareTo(current.data));
        if (compare == 0) {
          throw new IllegalArgumentException("This RedBlackTree already contains value " + data.toString());
        } else if (compare < 0) {
          // insert in left subtree
          if (current.context[1] == null) {
            // empty space to insert into
            current.context[1] = newNode;
            newNode.context[0] = current;
            this.size++;
            enforceRBTreePropertiesAfterInsert(newNode);
            return true;
          } else {
            // no empty space, keep moving down the tree
            current = current.context[1];
          }
        } else {
          // insert in right subtree
          if (current.context[2] == null) {
            // empty space to insert into
            current.context[2] = newNode;
            newNode.context[0] = current;
            this.size++;
            enforceRBTreePropertiesAfterInsert(newNode);
            return true;
          } else {
            // no empty space, keep moving down the tree
            current = current.context[2];
          }
        }
      }
    }
  }

  /**
   * Performs the rotation operation on the provided nodes within this tree. When
   * the provided child is a left child of the provided parent, this method will
   * perform a right rotation. When the provided child is a right child of the
   * provided parent, this method will perform a left rotation. When the provided
   * nodes are not related in one of these ways, this method will throw an
   * IllegalArgumentException.
   * 
   * @param child  is the node being rotated from child to parent position
   *               (between these two node arguments)
   * @param parent is the node being rotated from parent to child position
   *               (between these two node arguments)
   * @throws IllegalArgumentException when the provided child and parent node
   *                                  references are not initially (pre-rotation)
   *                                  related that way
   */
  private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
    // checking if the arguments are correct
    if (parent.context[1] == child) {
      rightrotate(parent);

    } else if (parent.context[2] == child) {
      leftrotate(parent);
    } else {
      throw new IllegalArgumentException("Illegal Arguments");
    }
  }

  /**
   * private helper method done for a right rotation. Works by replying child node
   * and parent and creating new link to the new parent value.
   * 
   * @param parent
   */
  private void rightrotate(Node<T> parent) {
    Node<T> child = parent.context[1];
    Node<T> b = child.context[2];
    replaceNode(parent, child);
    parent.context[1] = b;
    if (b != null) {
      b.context[0] = parent; // makes sure that the inital childs value is also placed in accordance of the
                             // valid BST
    }
    child.context[2] = parent;
    parent.context[0] = child; // sets the rotation values parent as the child that has replaced initial parent
  }

  /**
   * private helper method done for a left rotation. Works by replying child node
   * and parent and creating new link to the new parent value.
   * 
   * @param parent
   */
  private void leftrotate(Node<T> parent) {
    Node<T> child = parent.context[2];
    Node<T> b = child.context[1];
    replaceNode(parent, child);
    parent.context[2] = b;
    if (b != null) {
      b.context[0] = parent; // makes sure that the inital childs value is also placed in accordance of the
                             // valid BST
    }
    child.context[1] = parent;
    parent.context[0] = child; // sets the rotation values parent as the child that has replaced initial parent
  }

  /**
   * Get the size of the tree (its number of nodes).
   * 
   * @return the number of nodes in the tree
   */
  public int size() {
    return size;
  }

  /**
   * Method to check if the tree is empty (does not contain any node).
   * 
   * @return true of this.size() return 0, false if this.size() > 0
   */
  public boolean isEmpty() {
    return this.size() == 0;
  }

  /**
   * 
   * @param node
   * @return left child
   */
  @SuppressWarnings("unchecked")
  public T getLeft(T data) {

    Node node = get(data);
    if(node.context[1] != null)
    {
      return (T) node.context[1].data;
    }
    return null;
  }

  /**
   * 
   * @param node
   * @return right child
   */
  @SuppressWarnings("unchecked")
  public T getRight(T data) {

    Node node = get(data);
    if(node.context[2] != null)
    {
      return (T) node.context[2].data;
    }
    return null;
  }
  
  public T getRoot() {
    return (T)root.data;
  }
  /**
   * Removes the value data from the tree if the tree contains the value. This
   * method will not attempt to rebalance the tree after the removal and should be
   * updated once the tree uses Red-Black Tree insertion.
   * 
   * @return true if the value was remove, false if it didn't exist
   * @throws NullPointerException     when the provided data argument is null
   * @throws IllegalArgumentException when data is not stored in the tree
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
public boolean remove(T key) throws IllegalArgumentException, NullPointerException {
   
    
    Node<T> node = this.root; // set the starting node as the root of the tree

    while (node != null && key.compareTo((T) node.data) != 0) { // while the node is not null and the key is not found
      if (key.compareTo((T) node.data) > 0) { // if the key is greater than the current node's data, traverse right
        node = node.context[2];
      } else { // if the key is less than or equal to the current node's data, traverse left
        node = node.context[1];
      }
    }

    if (node == null) { // if the node is not found, throw an IllegalArgumentException
      throw new IllegalArgumentException();
    }
    System.out.println(node.data);
    Node movedUpNode;
    int deletedNodeColor;

    if (node.context[1] == null || node.context[2] == null) { // if the node has zero or one child
      movedUpNode = deleteNodeZeroOne(node); // delete the node with zero or one child

      deletedNodeColor = node.blackHeight; // set the deleted node's color
    } else { // if the node has two children
      Node inOrderSuccessor = findMinOfRightSubtree(node.context[2]); // find the minimum node of the right subtree
      node.data = (T) inOrderSuccessor.data; // copy inorder successor's data to current node
      movedUpNode = deleteNodeZeroOne(inOrderSuccessor); // delete inorder successor
      deletedNodeColor = inOrderSuccessor.blackHeight; // set the deleted node's color
    }

    if (deletedNodeColor == 1) { // if the deleted node's color is black
      //System.out.println(movedUpNode.data);
      if (getSibling(movedUpNode) != null) {
      fixRedBlackPropertiesAfterDelete(movedUpNode); 
      }// fix the Red-Black tree properties after deletion
      if (movedUpNode.getClass() == NilNode.class) { // if the node is temporary NIL, remove it
        replaceParentsChild(movedUpNode.context[0], movedUpNode, null);
      }
    }
    size--;
    return true; // return true if the node is successfully removed
  }
  /**
   * Checks whether the tree contains the value *data*.
   * 
   * @param data the data value to test for
   * @return true if *data* is in the tree, false if it is not in the tree
   */
  public boolean contains(T data) {
    // null references will not be stored within this tree
    if (data == null) {
      throw new NullPointerException("This RedBlackTree cannot store null references.");
    } else {
      Node<T> nodeWithData = this.get(data);
      // return false if the node is null, true otherwise
      return (nodeWithData != null);
    }
  }

  /**
   * Helper method that will replace a node with a replacement node. The
   * replacement node may be null to remove the node from the tree.
   * 
   * @param nodeToReplace   the node to replace
   * @param replacementNode the replacement for the node (may be null)
   */
  protected void replaceNode(Node<T> nodeToReplace, Node<T> replacementNode) {
    if (nodeToReplace == null) {
      throw new NullPointerException("Cannot replace null node.");
    }
    if (nodeToReplace.context[0] == null) {
      // we are replacing the root
      if (replacementNode != null)
        replacementNode.context[0] = null;
      this.root = replacementNode;
    } else {
      // set the parent of the replacement node
      if (replacementNode != null)
        replacementNode.context[0] = nodeToReplace.context[0];
      // do we have to attach a new left or right child to our parent?
      if (nodeToReplace.isRightChild()) {
        nodeToReplace.context[0].context[2] = replacementNode;
      } else {
        nodeToReplace.context[0].context[1] = replacementNode;
      }
    }
  }

  private void rotateRight(Node node) {
    Node parent = node.context[0];
    Node leftChild = node.context[1];

    node.context[1] = leftChild.context[2];
    if (leftChild.context[2] != null) {
      leftChild.context[2].context[0] = node;
    }

    leftChild.context[2] = node;
    node.context[0] = leftChild;

    replaceParentsChild(parent, node, leftChild);
  }

  private void rotateLeft(Node node) {
    Node parent = node.context[0];
    Node rightChild = node.context[2];

    node.context[2] = rightChild.context[1];
    if (rightChild.context[1] != null) {
      rightChild.context[1].context[0] = node;
    }

    rightChild.context[1] = node;
    node.context[0] = rightChild;

    replaceParentsChild(parent, node, rightChild);
  }

  private void replaceParentsChild(Node parent, Node oldChild, Node newChild) {
    if (parent == null) {
      root = newChild;
    } else if (parent.context[1] == oldChild) {
      parent.context[1] = newChild;
    } else if (parent.context[2] == oldChild) {
      parent.context[2] = newChild;
    } else {
      throw new IllegalStateException("Node is not a child of its parent");
    }

    if (newChild != null) {
      newChild.context[0] = parent;
    }
  }

  /**
   * Helper method that will return the inorder successor of a node with two
   * children.
   * 
   * @param node the node to find the successor for
   * @return the node that is the inorder successor of node
   */
  protected Node findMinOfRightSubtree(Node node) {
    if (node.context[2] == null) {
      return node;
    } else if (node.context[2].context[1] == null) {
      return node.context[2];
    } else {
      Node current = node.context[1];
      while (current.context[1] != null) {
        current = current.context[1];
      }
      return current;
    }
  }

  /**
   * Helper method that will return the node in the tree that contains a specific
   * value. Returns null if there is no node that contains the value.
   * 
   * @return the node that contains the data, or null of no such node exists
   */
  protected Node<T> get(T data) {
    Node<T> current = this.root;
    while (current != null) {
      int compare = data.compareTo(current.data);
      if (compare == 0) {
        // we found our value
        return current;
      } else if (compare < 0) {
        // keep looking in the left subtree
        current = current.context[1];
      } else {
        // keep looking in the right subtree
        current = current.context[2];
      }
    }
    // we're at a null node and did not find data, so it's not in the tree
    return null;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private Node deleteNodeZeroOne(Node node) {
    // Check if node has ONLY a left child
    if (node.context[1] != null) {
      // Replace node with its left child and return the moved-up node
      replaceParentsChild(node.context[0], node, node.context[1]);
      return node.context[1];
    }

    // Check if node has ONLY a right child
    if (node.context[2] != null) {
      // Replace node with its right child and return the moved-up node
      replaceParentsChild(node.context[0], node, node.context[2]);
      return node.context[2];
    }

    // Check if node has no children
    else {
      // Create a new NIL node if node is black, otherwise set newChild to null
      Node newChild = node.blackHeight == 1 ? new NilNode() : null;

      // Replace node with newChild and return the newChild
      replaceParentsChild(node.context[0], node, newChild);
      return newChild;
    }
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private static class NilNode extends Node {
    // Constructor for NIL node
    private NilNode() {
      super(0);
      // Set black height to 1 for NIL node
      this.blackHeight = 1;
    }
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private void fixRedBlackPropertiesAfterDelete(Node<T> node) {
    // Case 1: Examined node is root, end of recursion
    if (node == root) {
      // Uncomment the following line if you want to enforce black roots (rule 2):
      node.blackHeight = 1;
      return;
    }

    // Get sibling node
    Node sibling = getSibling(node);

    // Case 2: Red sibling
    if (sibling.blackHeight == 1) {
      // Handle red sibling case
      handleRedSibling(node, sibling);
      // Get new sibling for fall-through to cases 3-6
      sibling = getSibling(node);
    }

    // Cases 3+4: Black sibling with two black children
    if (sibling != null && isBlack(sibling.context[1]) && isBlack(sibling.context[2])) {
      // Set sibling black height to 0
      sibling.blackHeight = 0;

      // Case 3: Black sibling with two black children + red parent
      if (node.context[0].blackHeight == 0) {
        // Set parent black height to 1
        node.context[0].blackHeight = 1;
      }

      // Case 4: Black sibling with two black children + black parent
      else {
        // Recursively fix red-black properties after delete
        fixRedBlackPropertiesAfterDelete(node.context[0]);
      }
    }

    // Case 5+6: Black sibling with at least one red child
    else {
      // Handle black sibling with at least one red child case
      handleBlackSiblingWithAtLeastOneRedChild(node, sibling);
    }
  }
  
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private void handleRedSibling(Node node, Node sibling) {
    // Recolor nodes
    sibling.blackHeight = 1;
    node.context[0].blackHeight = 0;

    // Perform rotation
    if (node == node.context[0].context[1]) {
      rotateLeft(node.context[0]);
    } else {
      rotateRight(node.context[0]);
    }
  }

  @SuppressWarnings({})
  private void handleBlackSiblingWithAtLeastOneRedChild(Node<T> node, Node<T> sibling) {
    // Determine if the node is a left child or a right child
    boolean nodeIsLeftChild = !node.isRightChild();

    // Case 5: Black sibling with at least one red child + "outer nephew" is black
    // Recolor the sibling and its child, and rotate around the sibling
    if (sibling != null && nodeIsLeftChild && isBlack(sibling.context[2])) {
      System.out.print("11111");
      ;
      sibling.context[1].blackHeight = 1;
      sibling.blackHeight = 1;
      rotateRight(sibling);
      sibling = node.context[0].context[2];
    } else if (sibling != null && !nodeIsLeftChild && isBlack(sibling.context[1])) {
      System.out.print("22222");
      sibling.context[2].blackHeight = 1;
      sibling.blackHeight = 0;
      rotateLeft(sibling);
      sibling = node.context[0].context[1];
    }

    // Fall-through to case 6...

    // Case 6: Black sibling with at least one red child + "outer nephew" is red
    // Recolor the sibling, parent, and sibling's child, and rotate around the
    // parent
    if(sibling != null)
    {
      sibling.blackHeight = node.context[0].blackHeight;
      node.context[0].blackHeight = 1;
      if (nodeIsLeftChild) {
        sibling.context[2].blackHeight = 1;
  
        rotateLeft(node.context[0]);
      } else {
        sibling.context[1].blackHeight = 1;
  
        rotateRight(node.context[0]);
      }
    }
  }

  @SuppressWarnings("rawtypes")
  private Node getSibling(Node node) {
    // Get the parent of the node
    Node parent = node.context[0];
    // Determine which child of the parent is the node
    if (node == parent.context[1]) {
      return parent.context[2];
    } else if (node == parent.context[2]) {
      return parent.context[1];
    } else {
      throw new IllegalStateException("Parent is not a child of its grandparent");
    }
  }

  @SuppressWarnings("rawtypes")
  private boolean isBlack(Node node) {
    // Return true if the node is null or has a black height of 1
    return node == null || node.blackHeight == 1;
  }

  /**
   * This method performs an inorder traversal of the tree. The string
   * representations of each data value within this tree are assembled into a
   * comma separated string within brackets (similar to many implementations of
   * java.util.Collection, like java.util.ArrayList, LinkedList, etc).
   * 
   * @return string containing the ordered values of this tree (in-order
   *         traversal)
   */
  public String toInOrderString() {
    // generate a string of all values of the tree in (ordered) in-order
    // traversal sequence
    StringBuffer sb = new StringBuffer();
    sb.append("[ ");
    if (this.root != null) {
      Stack<Node<T>> nodeStack = new Stack<>();
      Node<T> current = this.root;
      while (!nodeStack.isEmpty() || current != null) {
        if (current == null) {
          Node<T> popped = nodeStack.pop();
          sb.append(popped.data.toString());
          if (!nodeStack.isEmpty() || popped.context[2] != null)
            sb.append(", ");
          current = popped.context[2];
        } else {
          nodeStack.add(current);
          current = current.context[1];
        }
      }
    }
    sb.append(" ]");
    return sb.toString();
  }

  /**
   * This method performs a level order traversal of the tree. The string
   * representations of each data value within this tree are assembled into a
   * comma separated string within brackets (similar to many implementations of
   * java.util.Collection). This method will be helpful as a helper for the
   * debugging and testing of your rotation implementation.
   * 
   * @return string containing the values of this tree in level order
   */
  public String toLevelOrderString() {
    StringBuffer sb = new StringBuffer();
    sb.append("[ ");
    if (this.root != null) {
      LinkedList<Node<T>> q = new LinkedList<>();
      q.add(this.root);
      while (!q.isEmpty()) {
        Node<T> next = q.removeFirst();
        if (next.context[1] != null)
          q.add(next.context[1]);
        if (next.context[2] != null)
          q.add(next.context[2]);
        sb.append(next.data.toString());
        if (!q.isEmpty())
          sb.append(", ");
      }
    }
    sb.append(" ]");
    return sb.toString();
  }

  public String toString() {
    return "level order: " + this.toLevelOrderString() + "\nin order: " + this.toInOrderString();
  }

}
