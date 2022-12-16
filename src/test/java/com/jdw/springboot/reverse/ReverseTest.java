package com.jdw.springboot.reverse;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ReverseTest {
    public static class ListNode {

        private int data;
        private ListNode next;

        ListNode(int data) {
            this.data = data;
            this.next = null;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public ListNode getNext() {
            return next;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }
    }

    ListNode constructLinkedList() {
        ListNode head = null;
        ListNode tail = null;
        for (int i = 1; i <= 5; i++) {
            ListNode node = new ListNode(i);
            if (head == null) {
                head = node;
            } else {
                tail.setNext(node);
            }
            tail = node;
        }
        return head;
    }

    ListNode reverseList1(ListNode head) {
        ListNode current = head;
        ListNode result = null;
        while (current != null) {
            ListNode node = current.getNext();
            current.setNext(result);
            result = current;
            current = node;
        }
        return result;
    }
    @Test
    public void givenLinkedList_whenIterativeReverse_thenOutputCorrectResult() {
        ListNode head = constructLinkedList();
        ListNode node = head;
        for (int i = 1; i <= 5; i++) {
            assertNotNull(node);
            assertEquals(i, node.getData());
            node = node.getNext();
        }

        node = reverseList1(head);

        for (int i = 5; i >= 1; i--) {
            assertNotNull(node);
            assertEquals(i, node.getData());
            node = node.getNext();
        }
    }

    @Test
    public void containAll() {
        List first = Arrays.asList(1, 3, 4, 6, 8);
        List second = Arrays.asList(8, 1, 6, 3, 4);
        List third = Arrays.asList(1, 3, 3, 6, 6);
        assertTrue(first.size() == second.size() && first.containsAll(second) && second.containsAll(first));
        assertThat(first).hasSameClassAs(second);
    }

}
