package com.revature.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class LinkedList <T> {

    // Points to the front and back of the LinkedList.
    public Node<T> head = null;
    public Node<T> tail = null;
    private int size = 0;

    public int getSize() {
        return size;
    }

    public void add(T data) {
        Node<T> newNode = new Node(data);

        if (head == null) {
            head = newNode;
            tail = newNode;
            size++;
        } else {
            tail.next = newNode;
            tail = newNode;
            size++;
        }
    }

    public T get(int index) {
        Node<T> iterator = head;

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is less than 0 or above the list's size.");
        }

        for (int i = 0; i < index; i++) {
            iterator = iterator.next;
        }

        return iterator.data;
    }

    public boolean checkIfExists(String name) {
        Node<T> iterator = head;

        for(int i = 0; i < size; i++) {
            T element = iterator.data;

            try {
                if (Objects.equals(element.getClass().getMethod("getUserLogin").invoke(element), name)) {
                    return true;
                }
            } catch (NoSuchMethodException e) {
                continue;
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
            iterator = iterator.next;
        }
        return false;
    }

    @Override
    public String toString() {
        Node<T> current = head;
        String result = "";

        while (current != null) {
            result += current.data;

            if (current != tail) {
                result += "\n";
            }
            current = current.next;
        }

        return result;
    }

}