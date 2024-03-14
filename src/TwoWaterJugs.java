import java.util.*;


class State {
    int jug1, jug2;

    public State(int jug1, int jug2) {
        this.jug1 = jug1;
        this.jug2 = jug2;
    }
}

public class TwoWaterJugs {
    // Kiểm tra trạng thái hợp lệ của bình nước
    static boolean isValidState(int jug1, int jug2, int maxJug1, int maxJug2) {
        return jug1 >= 0 && jug2 >= 0 && jug1 <= maxJug1 && jug2 <= maxJug2;
    }

    // Tạo ra các trạng thái kế tiếp từ trạng thái hiện tại
    static List<State> getNextStates(State currentState, int maxJug1, int maxJug2) {
        List<State> nextStates = new ArrayList<>();

        // Đổ nước từ bình 1 sang bình 2
        nextStates.add(new State(Math.max(0, currentState.jug1 - (maxJug2 - currentState.jug2)),
                Math.min(maxJug2, currentState.jug1 + currentState.jug2)));

        // Đổ nước từ bình 2 sang bình 1
        nextStates.add(new State(Math.min(maxJug1, currentState.jug1 + currentState.jug2),
                Math.max(0, currentState.jug2 - (maxJug1 - currentState.jug1))));

        // Đổ nước ra khỏi bình 1
        nextStates.add(new State(0, currentState.jug2));

        // Đổ nước ra khỏi bình 2
        nextStates.add(new State(currentState.jug1, 0));

        // Đổ đầy bình 1
        nextStates.add(new State(maxJug1, currentState.jug2));

        // Đổ đầy bình 2
        nextStates.add(new State(currentState.jug1, maxJug2));

        return nextStates;
    }

    // Kiểm tra trạng thái mục tiêu
    static boolean isGoalState(State currentState, int goal) {
        return currentState.jug1 == goal || currentState.jug2 == goal;
    }

    // Thực hiện thuật toán BFS
    static void BFS(int maxJug1, int maxJug2, int goal) {
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();
        Map<State, State> parent = new HashMap<>();

        State initialState = new State(0, 0);
        queue.add(initialState);
        visited.add(initialState);

        while (!queue.isEmpty()) {
            State currentState = queue.poll();

            if (isGoalState(currentState, goal)) {

                List<State> path = new ArrayList<>();
                while (currentState != null) {
                    path.add(currentState);
                    currentState = parent.get(currentState);
                }
                Collections.reverse(path);
                for (State state : path) {
                    System.out.println("Jug1: " + state.jug1 + " liters, Jug2: " + state.jug2 + " liters");
                }
                return;
            }

            // Tạo các trạng thái kế tiếp từ trạng thái hiện tại
            List<State> nextStates = getNextStates(currentState, maxJug1, maxJug2);
            for (State nextState : nextStates) {
                if (!visited.contains(nextState)) {
                    queue.add(nextState);
                    visited.add(nextState);
                    parent.put(nextState, currentState);
                }
            }
        }


        System.out.println("Không tìm thấy đường đi đến trạng thái mục tiêu.");
    }

    public static void main(String[] args) {
        int jug1Capacity = 4;
        int jug2Capacity = 3;
        int goal = 2;

        System.out.println("Các bước để đạt được " + goal + " lít nước:");
        BFS(jug1Capacity, jug2Capacity, goal);
    }
}