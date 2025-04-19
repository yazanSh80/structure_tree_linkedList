package com.example.project2;

import java.time.LocalDate;

public class LocationBinaryTree {

    private static LocationNode root;

    public void addLocation(String location) {
        root = addLocationRecursive(root, location);
    }

    private LocationNode addLocationRecursive(LocationNode node, String location) {
        if (node == null) {
            return new LocationNode(location);
        }

        int compareResult = location.compareToIgnoreCase(node.getLocation());
        if (compareResult < 0) {
            node.setLeft(addLocationRecursive(node.getLeft(), location));
        } else if (compareResult > 0) {
            node.setRight(addLocationRecursive(node.getRight(), location));
        }
        // if compareResult == 0, location already exists, do nothing

        return node;
    }

    public boolean locationExists(String location) {
        LocationNode current = root;
        while (current != null) {
            int compareResult = location.compareToIgnoreCase(current.getLocation());
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

    public void removeLocation(String location) {
        root = removeLocation(root, location);
    }

    private LocationNode removeLocation(LocationNode node, String location) {
        if (node == null) {
            return null;
        }

        int compareResult = location.compareToIgnoreCase(node.getLocation());
        if (compareResult < 0) {
            node.setLeft(removeLocation(node.getLeft(), location));
        } else if (compareResult > 0) {
            node.setRight(removeLocation(node.getRight(), location));
        } else {
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            }

            LocationNode minNode = findMinNode(node.getRight());
            node.setLocation(minNode.getLocation());
            node.setRight(removeLocation(node.getRight(), minNode.getLocation()));
        }

        return node;
    }

    private LocationNode findMinNode(LocationNode node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    private LocationNode addLocationRecursive1(LocationNode node, String location) {
        if (node == null) {
            return new LocationNode(location);
        }

        int compareResult = location.compareToIgnoreCase(node.getLocation());
        if (compareResult < 0) {
            node.setLeft(addLocationRecursive(node.getLeft(), location));
        } else if (compareResult > 0) {
            node.setRight(addLocationRecursive(node.getRight(), location));
        }

        return node;
    }

    public void updateLocation(String oldLocation, String newLocation) {
        removeLocation(oldLocation);
        addLocation(newLocation);
    }

    public LocationNode searchLocation(String location) {
        return searchLocationRecursive(root, location);
    }

    private LocationNode searchLocationRecursive(LocationNode currentNode, String targetLocation) {
        if (currentNode == null || targetLocation.equals(currentNode.getLocation())) {
            return currentNode;
        }

        int comparisonResult = targetLocation.compareTo(currentNode.getLocation());

        if (comparisonResult < 0) {
            return searchLocationRecursive(currentNode.getLeft(), targetLocation);
        } else {
            return searchLocationRecursive(currentNode.getRight(), targetLocation);
        }
    }

    private DistrictNode findNode(DistrictNode root, String district) {
        if (root == null) {
            return null;
        }

        int compareResult = district.compareToIgnoreCase(root.getDistrict());
        if (compareResult == 0) {
            return root;
        } else if (compareResult < 0) {
            return findNode(root.getLeft(), district);
        } else {
            return findNode(root.getRight(), district);
        }
    }

    public DistrictNode getRoot() {
        return null;
    }

    public DistrictNode findPreviousNode(DistrictNode root2, LocalDate currentDate) {
        return null;
    }

    public DistrictNode findNextNode(DistrictNode root2, LocalDate currentDate) {
        return null;
    }

//	public static void insert(String newLocation) {
//		root = insertRecursive(root, newLocation);
//	}
//
//	private static LNodeTree insertRecursive(LNodeTree node, String newLocation) {
//		if (node == null) {
//			return new LNodeTree(newLocation);
//		}
//
//		int compareResult = newLocation.compareToIgnoreCase(node.getLocation());
//		if (compareResult < 0) {
//			node.setLeft(insertRecursive(node.getLeft(), newLocation));
//		} else if (compareResult > 0) {
//			node.setRight(insertRecursive(node.getRight(), newLocation));
//		}
//
//		return node;
//	}
//
//	public static LNodeTree search(String location) {
//		return searchRecursive(root, location);
//	}
//
//	private static LNodeTree searchRecursive(LNodeTree node, String location) {
//		if (node == null) {
//			return null;
//		}
//
//		if (node.getLocation().equalsIgnoreCase(location)) {
//			return node;
//		} else if (node.getLocation().compareToIgnoreCase(location) > 0) {
//			return searchRecursive(node.getLeft(), location);
//		} else {
//			return searchRecursive(node.getRight(), location);
//		}
//	}
//
//	public static void delete(String selectedLocation) {
//		root = deleteRecursive(root, selectedLocation);
//	}
//
//	private static LNodeTree deleteRecursive(LNodeTree node, String selectedLocation) {
//		if (node == null) {
//			return null;
//		}
//
//		int compareResult = selectedLocation.compareToIgnoreCase(node.getLocation());
//		if (compareResult < 0) {
//			node.setLeft(deleteRecursive(node.getLeft(), selectedLocation));
//		} else if (compareResult > 0) {
//			node.setRight(deleteRecursive(node.getRight(), selectedLocation));
//		} else {
//			if (node.getLeft() == null) {
//				return node.getRight();
//			} else if (node.getRight() == null) {
//				return node.getLeft();
//			}
//
//			node.setLocation(minValue(node.getRight()));
//
//			node.setRight(deleteRecursive(node.getRight(), node.getLocation()));
//		}
//
//		return node;
//	}
//
//	private static String minValue(LNodeTree node) {
//		String minv = node.getLocation();
//		while (node.getLeft() != null) {
//			minv = node.getLeft().getLocation();
//			node = node.getLeft();
//		}
//		return minv;
//	}
}

