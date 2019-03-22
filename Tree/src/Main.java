import java.util.*;

class DegenTreeNode
{
    DegenTreeNode next;
    LinkedList<Integer> list = new LinkedList<>();

    public DegenTreeNode()
    {
        next = null;
    }

    public void setNext(DegenTreeNode newNode)
    {
        next = newNode;
    }
}

class TreeNode {
    TreeNode left;
    TreeNode right;
    LinkedList<Integer> list = new LinkedList<>();

    public TreeNode()
    {
        left = null;
        right = null;
        list = null;
    }

    public TreeNode(LinkedList<Integer> data)
    {
        list = data;
    }

    public TreeNode balanceTree(LinkedList<Integer>[] arr, int start, int end)
    {
        if(start > end)
        {
            return null;
        }

        int middle = (start + end)/2;
        TreeNode midNode = new TreeNode(arr[middle]);

        midNode.left = balanceTree(arr, start, middle - 1);
        midNode.right = balanceTree(arr, middle + 1, end);

        return midNode;
    }
}

public class Main {

    public static void main(String[] args) {
        // create degen tree
        DegenTreeNode degenHead = new DegenTreeNode();
        degenHead.list = new LinkedList<>();
        degenHead.list.add(21);
        degenHead.list.add(7);
        degenHead.list.add(15);
        DegenTreeNode degenTemp = degenHead;
        degenTemp.setNext(new DegenTreeNode());
        degenTemp = degenTemp.next;
        degenTemp.list.add(31);
        degenTemp.list.add(5);
        degenTemp.list.add(24);
        degenTemp.list.add(9);
        degenTemp.list.add(99);
        degenTemp.setNext(new DegenTreeNode());
        degenTemp = degenTemp.next;
        degenTemp.list.add(4);
        degenTemp.list.add(36);
        degenTemp.list.add(78);
        degenTemp.setNext(new DegenTreeNode());
        degenTemp = degenTemp.next;
        degenTemp.list.add(72);
        degenTemp.list.add(60);
        degenTemp.list.add(58);
        degenTemp.list.add(47);
        degenTemp.list.add(2);
        degenTemp.setNext(new DegenTreeNode());
        degenTemp = degenTemp.next;
        degenTemp.list.add(89);
        degenTemp.list.add(47);
        degenTemp.list.add(69);
        degenTemp.setNext(new DegenTreeNode());
        degenTemp = degenTemp.next;
        degenTemp.list.add(8);
        degenTemp.list.add(30);
        degenTemp.list.add(55);
        degenTemp.list.add(1);
        degenTemp.list.add(12);
        degenTemp.list.add(16);
        degenTemp.setNext(new DegenTreeNode());
        degenTemp = degenTemp.next;
        degenTemp.list.add(7);
        degenTemp.list.add(10);
        degenTemp.list.add(42);


        algorithm(degenHead);
    }

    public static void algorithm(DegenTreeNode treeHead)
    {
        LinkedList<Integer>[] arr = treeToArray(loadTree(radixSort(saveTree(treeHead))));
        TreeNode treeNode = new TreeNode();
        int n = arr.length;
        TreeNode balancedTree = treeNode.balanceTree(arr, 0, n - 1);
        printNode(balancedTree);
    }

    public static StringBuilder saveTree(DegenTreeNode treeHead)
    {
        StringBuilder string = new StringBuilder();
        DegenTreeNode temp = treeHead;
        while(temp != null)
        {
            LinkedList<Integer> listTemp = temp.list;
            ListIterator<Integer> iterator = listTemp.listIterator();

            while(iterator.hasNext())
            {
                string.append(iterator.next());
                string.append(' ');
            }
            string.append('#');
            string.append(' ');
            temp = temp.next;
        }
        System.out.println(string);
        return string;
    }

    static StringBuilder radixSort(StringBuilder sb)
    {
        StringBuilder sorted = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        ArrayList<Integer> arr = new ArrayList<>();

        for (int i = 0; i < sb.length() - 1; i++)
        {
            char c = sb.charAt(i);
            if(c == ' ' && (sb.charAt(i+1) == '#' || sb.charAt(i-1) == '#'))
            { }
            else if(c == '#')
            {
                String[] numbers = temp.toString().split(" ");
                for (String number : numbers)
                {
                    arr.add(Integer.parseInt(number));
                }
                arr.removeAll(Collections.singleton(null));
                int[] newArray = new int[arr.size()];

                for(int num = 0; num < newArray.length; num++)
                {
                    newArray[num] = arr.get(num);
                }

                int j;
                int length = newArray.length;
                int max = getMax(newArray, length);

                int max_digits = 0;
                while(max != 0)
                {
                    max /= 10;
                    max_digits++;
                }

                radix(newArray, max_digits);

                for(j = 0; j < length; j++)
                {
                    sorted.append(newArray[j]);
                    sorted.append(" ");
                }
                sorted.append("# ");
                arr.clear();
                temp.setLength(0);
            }
            else
            {
                temp.append(c);
            }
        }
        System.out.println(sorted);

        return sorted;
    }

    public static void radix(int[] array, int max_digits){
        int divider = 1;
        int mod = 10;

        int[] output = new int[array.length];

        for (int d=0; d<max_digits; d++) {

            int[] count = new int[10];

            for (int i=0; i<10; i++) {
                count[i] = 0;
            }

            for (int i=0; i<array.length; i++) {
                int digit = (array[i]%mod)/divider;
                count[digit]++;
            }

            for (int i=1; i<10; i++)
                count[i] = count[i]+count[i-1];

            for (int i=array.length-1; i>=0; i--){
                int digit = (array[i]%mod)/divider;
                output[count[digit]-1] = array[i];
                count[digit]--;
            }

            for (int i=0; i<array.length; i++) {
                array[i] = output[i];
            }

            mod = mod*10;
            divider = divider*10;
        }
    }

    static int getMax(int[] arr, int n)
    {
        int max = arr[0];
        for(int i = 1; i < n; i++)
        {
            if(arr[i] > max)
            {
                max = arr[i];
            }
        }
        return max;
    }

    public static DegenTreeNode loadTree(StringBuilder string)
    {
        DegenTreeNode head = new DegenTreeNode();
        DegenTreeNode node = head;
        StringBuilder temp = new StringBuilder();

        for (int i = 0; i < string.length() - 1; i++)
        {
            if(string.charAt(i) == ' ' && (string.charAt(i+1) == '#' || string.charAt(i-1) == '#'))
            { }
            else if(string.charAt(i) == '#')
            {
                String[] numbers = temp.toString().split(" ");
                for (String number : numbers)
                {
                    node.list.add(Integer.parseInt(number));
                }
                if(i != string.length() - 2)
                {
                    node.setNext(new DegenTreeNode());
                }
                node = node.next;
                temp.setLength(0);
            }
		else
            {
                temp.append(string.charAt(i));
            }
        }
        return head;
    }


    public static LinkedList<Integer>[] treeToArray(DegenTreeNode treeHead)
    {
        ArrayList<LinkedList<Integer>> arr = new ArrayList<>();

        DegenTreeNode temp = treeHead;
        while(temp != null)
        {
            arr.add(temp.list);
            temp = temp.next;
        }
        arr.removeAll(Collections.singleton(null));
        return arr.toArray(new LinkedList[arr.size()]);
    }

    public static void printNode(TreeNode root) {
        int maxLevel = maxLevel(root);

        System.out.println("");
        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static void printNodeInternal(List<TreeNode> nodes, int level, int maxLevel)
    {
        if(nodes.isEmpty() || isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        //int edgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int edgeLines = (int) Math.pow(2, (Math.max(floor + 1, 0)));
        //int firstSpaces = (int) Math.pow(2, (floor) - 1);
        int firstSpaces = (int) Math.pow(2, (floor) + 4) - 10;
        //int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 15;

        printWhitespaces(firstSpaces);

        List<TreeNode> newNodes = new ArrayList<>();
        for (TreeNode node : nodes) {
            if (node != null)
            {
                if(level != 1)
                    printWhitespaces(12);
                System.out.print(node.list);
                newNodes.add(node.left);
                newNodes.add(node.right);
            }
            else
            {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= edgeLines; i++)
        {
            for (int j = 0; j < nodes.size(); j++)
            {
                printWhitespaces(firstSpaces - 5 - i);

                if (nodes.get(j) == null)
                {
                    printWhitespaces(edgeLines + i + i + 1);
                    continue;
                }

                if (nodes.get(j).left != null)
                {
                    printWhitespaces(8);
                    if(level != 1)
                        printWhitespaces(9);
                    System.out.print("/");
                }
                else
                    printWhitespaces(1);

                //printWhitespaces(i + i - 1);
                printWhitespaces(i + i + 4);

                if (nodes.get(j).right != null)
                    System.out.print("\\");
                else
                    printWhitespaces(1);

                printWhitespaces(edgeLines + edgeLines - 4 - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static int maxLevel(TreeNode node) {
        if (node == null)
            return 0;

        return Math.max(maxLevel(node.left), maxLevel(node.right)) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }
}
