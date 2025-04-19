package com.example.project2;

public class DistrictBinaryTree {

    private DistrictNode root;
    private DistrictNode current;

    public DistrictBinaryTree() {
        root = null;
    }

    public DistrictNode getRoot() {
        return root;
    }

    public void setRoot(DistrictNode root) {
        this.root = root;
    }

    public boolean addDistrict(String district) {
        if (root == null) {
            root = new DistrictNode(district);
        } else {
            addDistrict(root, district);
        }
        return false;
    }

    private void addDistrict(DistrictNode node, String district) {
        int compareResult = district.compareToIgnoreCase(node.getDistrict());
        if (compareResult < 0) {
            if (node.getLeft() == null) {
                node.setLeft(new DistrictNode(district));
            } else {
                addDistrict(node.getLeft(), district);
            }
        } else if (compareResult > 0) {
            if (node.getRight() == null) {
                node.setRight(new DistrictNode(district));
            } else {
                addDistrict(node.getRight(), district);
            }
        }
    }

    public void removeDistrict(String district) {
        root = removeDistrictRecursive(root, district);
    }

    private DistrictNode removeDistrictRecursive(DistrictNode node, String district) {
        if (node == null) {
            return null;
        }

        int compareResult = district.compareToIgnoreCase(node.getDistrict());
        if (compareResult < 0) {
            node.setLeft(removeDistrictRecursive(node.getLeft(), district));
        } else if (compareResult > 0) {
            node.setRight(removeDistrictRecursive(node.getRight(), district));
        } else {
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            }

            DistrictNode minNode = findMinNode(node.getRight());
            node.setDistrict(minNode.getDistrict());
            node.setRight(removeDistrictRecursive(node.getRight(), minNode.getDistrict()));
        }

        return node;
    }

    private DistrictNode findMinNode(DistrictNode node) {
        if (node == null) {
            return null;
        }

        while (node.getLeft() != null) {
            node = node.getLeft();
        }

        return node;
    }

    public boolean districtExists(String district) {
        DistrictNode current = root;
        while (current != null) {
            int compareResult = district.compareToIgnoreCase(current.getDistrict());
            if (compareResult == 0) {
                return true;
            } else if (compareResult < 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        return false;
    }

    public void updateDistrict(String district, String newDistrict) {
        root = updateDistrict(root, district, newDistrict);
    }

    private DistrictNode updateDistrict(DistrictNode node, String district, String newDistrict) {
        if (node == null) {
            return null;
        }

        int compareResult = district.compareToIgnoreCase(node.getDistrict());
        if (compareResult < 0) {
            node.setLeft(updateDistrict(node.getLeft(), district, newDistrict));
        } else if (compareResult > 0) {
            node.setRight(updateDistrict(node.getRight(), district, newDistrict));
        } else {
            node.setDistrict(newDistrict);
        }

        return node;
    }

    public void navigateInOrder() {
        Stack stack = new Stack();
        current = root;
        boolean done = false;

        while (!done) {
            if (current != null) {
                stack.push(current);
                current = current.getLeft();
            } else {
                if (!stack.isEmpty()) {
                    current = (DistrictNode) stack.pop();
                    System.out.println("Current District: " + current.getDistrict());
                    displayDistrictInformation(current);
                    System.out.println();
                    current = current.getRight();
                } else {
                    done = true;
                }
            }
        }
    }

    private void displayDistrictInformation(DistrictNode node) {
        System.out.println("Total number of martyrs: " + getTotalMartyrs(node));
        System.out.println("Option: Load current district's locations"); // Replace with your implementation
    }

    private int getTotalMartyrs(DistrictNode node) {
        if (node == null) {
            return 0;
        } else {
            return node.martyrs + getTotalMartyrs(node.getLeft()) + getTotalMartyrs(node.getRight());
        }
    }

    public boolean containsDistrict(String name) {
        return containsDistrictRecursive(root, name);
    }

    private boolean containsDistrictRecursive(DistrictNode node, String name) {
        if (node == null) {
            return false;
        }

        int compareResult = name.compareToIgnoreCase(node.getDistrict());
        if (compareResult == 0) {
            return true;
        } else if (compareResult < 0) {
            return containsDistrictRecursive(node.getLeft(), name);
        } else {
            return containsDistrictRecursive(node.getRight(), name);
        }
    }

    public int size() {
        return size(root);
    }

    private int size(DistrictNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.getLeft()) + size(node.getRight());
    }

    public String get(int i) {
        if (i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException("Index " + i + " is out of bounds");
        }
        IndexWrapper indexWrapper = new IndexWrapper();
        indexWrapper.index = 0;
        return get(root, i, indexWrapper);
    }

    private String get(DistrictNode node, int i, IndexWrapper indexWrapper) {
        if (node == null) {
            return null;
        }

        String left = get(node.getLeft(), i, indexWrapper);
        if (left != null) {
            return left;
        }

        if (indexWrapper.index == i) {
            return node.getDistrict();
        }
        indexWrapper.index++;

        return get(node.getRight(), i, indexWrapper);
    }

    private static class IndexWrapper {
        int index;
    }

    public void add(int i, String name) {
        if (i < 0 || i > size()) {
            throw new IndexOutOfBoundsException("Index " + i + " is out of bounds");
        }
        IndexWrapper indexWrapper = new IndexWrapper();
        indexWrapper.index = 0;
        add(root, i, name, indexWrapper);
    }

    private void add(DistrictNode node, int i, String name, IndexWrapper indexWrapper) {
        if (node == null) {
            throw new IndexOutOfBoundsException("Index " + i + " is out of bounds");
        }

        if (indexWrapper.index == i) {
            DistrictNode newNode = new DistrictNode(name);
            if (name.compareToIgnoreCase(node.getDistrict()) < 0) {
                newNode.setLeft(node.getLeft());
                node.setLeft(newNode);
            } else {
                newNode.setRight(node.getRight());
                node.setRight(newNode);
            }
        } else if (indexWrapper.index < i) {
            indexWrapper.index++;
            add(node.getRight(), i, name, indexWrapper);
        } else {
            indexWrapper.index++;
            add(node.getLeft(), i, name, indexWrapper);
        }
    }

    public String getData() {
        // Implement logic to return the district's data as a string
        return ""; // Placeholder return statement
    }

}
