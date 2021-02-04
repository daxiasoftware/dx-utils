package com.daxiasoftware.utils;

public class ObjectPair<K, V> {
    private K left; 
    private V right;
    
    public ObjectPair(K left, V right) {
        this.left = left;
        this.right = right;
    }
    public K getLeft() {
        return left;
    }
    public void setLeft(K left) {
        this.left = left;
    }
    public V getRight() {
        return right;
    }
    public void setRight(V right) {
        this.right = right;
    }

    public static void main(String[] args) throws Exception {
        new ObjectPair<String, Integer>("a", 2);
    }
    
}
