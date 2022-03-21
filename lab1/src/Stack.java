public class Stack<T> {
    T[] element;
    int size;
    int CAPACITY = 10;
    int pointer = -1;

    public Stack(int capacity) {
        CAPACITY = capacity;
        this.element = (T[]) new Object[CAPACITY];
    }

    private void resize () {
        CAPACITY = (int) (CAPACITY * 1.5);
        T[] temp = (T[]) new Object[CAPACITY];
        System.arraycopy(this.element, 0, temp, 0, this.element.length);
        this.element = temp;
    }

    public void push(T t) {
        //TODO: complete the method, push an element into the stack if the stack is not full.
        if (pointer == CAPACITY - 1) return;
        pointer ++;
        element[pointer] = t;
    }

    public T pop() {
        //TODO: complete the method, pop and return the top element from stack if it is not empty.
        if (pointer == -1) return null;
        T t = this.element[pointer];
        pointer --;
        return t;
    }

    public void showElements() {
        //TODO: complete the method, print all elements from the bottom to top.
        for (int i = 0; i <= pointer; i ++) {
            System.out.print(element[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>(5);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.push(6);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        stack.push(10);
        stack.showElements();
    }
}