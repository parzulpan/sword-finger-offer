package Java;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author parzulpan

460.LFU 缓存

请你为 最不经常使用（LFU）缓存算法设计并实现数据结构。

实现 LFUCache 类：

LFUCache(int capacity) - 用数据结构的容量 capacity 初始化对象
int get(int key) - 如果键存在于缓存中，则获取键的值，否则返回 -1。
void put(int key, int value) - 如果键已存在，则变更其值；如果键不存在，请插入键值对。当缓存达到其容量时，则应该在插入新项之前，使最不经常使用的项无效。在此问题中，当存在平局（即两个或更多个键具有相同使用频率）时，应该去除 最近最久未使用 的键。

注意「项的使用次数」就是自插入该项以来对其调用 get 和 put 函数的次数之和。使用次数会在对应项被移除后置为 0 。
为了确定最不常使用的键，可以为缓存中的每个键维护一个 使用计数器 。使用计数最小的键是最久未使用的键。
当一个键首次插入到缓存中时，它的使用计数器被设置为 1 (由于 put 操作)。对缓存中的键执行 get 或 put 操作，使用计数器的值将会递增。

提示：
0 <= capacity, key, value <= 104
最多调用 105 次 get 和 put 方法

 */

public class LFUCache {
    public static void main(String[] args) {
        // cnt(x) = 键 x 的使用计数
        // cache=[] 将显示最后一次使用的顺序（最左边的元素是最近的）
        LFUCache lfuCache = new LFUCache(2);

        // cache=[1,_], cnt(1)=1
        lfuCache.put(1, 1);

        // cache=[2,1], cnt(2)=1, cnt(1)=1
        lfuCache.put(2, 2);

        // 返回 1
        // cache=[1,2], cnt(2)=1, cnt(1)=2
        lfuCache.get(1);

        // 去除键 2 ，因为 cnt(2)=1 ，使用计数最小
        lfuCache.put(3, 3);
        // cache=[3,1], cnt(3)=1, cnt(1)=2
        lfuCache.get(2);      // 返回 -1（未找到）
        lfuCache.get(3);      // 返回 3
        // cache=[3,1], cnt(3)=2, cnt(1)=2
        lfuCache.put(4, 4);   // 去除键 1 ，1 和 3 的 cnt 相同，但 1 最久未使用
        // cache=[4,3], cnt(4)=1, cnt(3)=2
        lfuCache.get(1);      // 返回 -1（未找到）
        lfuCache.get(3);      // 返回 3
        // cache=[3,4], cnt(4)=1, cnt(3)=3
        lfuCache.get(4);      // 返回 4
        // cache=[3,4], cnt(4)=2, cnt(3)=3
    }

    int minfreq, capacity;
    Map<Integer, Node> keyTable;
    Map<Integer, LinkedList<Node>> freqTable;

    public LFUCache(int capacity) {
        this.minfreq = 0;
        this.capacity = capacity;
        keyTable = new HashMap<Integer, Node>();;
        freqTable = new HashMap<Integer, LinkedList<Node>>();
    }

    public int get(int key) {
        if (capacity == 0) {
            return -1;
        }
        if (!keyTable.containsKey(key)) {
            return -1;
        }
        Node node = keyTable.get(key);
        int val = node.val, freq = node.freq;
        freqTable.get(freq).remove(node);

        // 如果当前链表为空，我们需要在哈希表中删除，且更新minFreq
        if (freqTable.get(freq).size() == 0) {
            freqTable.remove(freq);
            if (minfreq == freq) {
                minfreq += 1;
            }
        }
        // 插入到 freq + 1 中
        LinkedList<Node> list = freqTable.getOrDefault(freq + 1, new LinkedList<Node>());
        list.offerFirst(new Node(key, val, freq + 1));
        freqTable.put(freq + 1, list);
        keyTable.put(key, freqTable.get(freq + 1).peekFirst());
        return val;
    }

    public void put(int key, int value) {
        if (capacity == 0) {
            return;
        }
        if (!keyTable.containsKey(key)) {
            // 缓存已满，需要进行删除操作
            if (keyTable.size() == capacity) {
                // 通过 minFreq 拿到 freq_table[minFreq] 链表的末尾节点
                Node node = freqTable.get(minfreq).peekLast();
                keyTable.remove(node.key);
                freqTable.get(minfreq).pollLast();
                if (freqTable.get(minfreq).size() == 0) {
                    freqTable.remove(minfreq);
                }
            }
            LinkedList<Node> list = freqTable.getOrDefault(1, new LinkedList<Node>());
            list.offerFirst(new Node(key, value, 1));
            freqTable.put(1, list);
            keyTable.put(key, freqTable.get(1).peekFirst());
            minfreq = 1;
        } else {
            // 与 get 操作基本一致，除了需要更新缓存的值
            Node node = keyTable.get(key);
            int freq = node.freq;
            freqTable.get(freq).remove(node);
            if (freqTable.get(freq).size() == 0) {
                freqTable.remove(freq);
                if (minfreq == freq) {
                    minfreq += 1;
                }
            }
            LinkedList<Node> list = freqTable.getOrDefault(freq + 1, new LinkedList<Node>());
            list.offerFirst(new Node(key, value, freq + 1));
            freqTable.put(freq + 1, list);
            keyTable.put(key, freqTable.get(freq + 1).peekFirst());
        }
    }
}

class Node {
    int key, val, freq;

    Node(int key, int val, int freq) {
        this.key = key;
        this.val = val;
        this.freq = freq;
    }
}