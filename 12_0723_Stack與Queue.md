# 7/23 教材：Stack 與 Queue

## 單元名稱

Stack、Queue、Circular Queue 與 ArrayDeque

## 課程定位

本單元將 Node 與陣列概念轉換為具有明確資料進出規則的 ADT。先手動實作固定容量 Stack 與 Circular Queue，再使用 Java ArrayDeque 完成 Undo、括號檢查、叫號與工作流程。

## 學習目標

1. 解釋 LIFO、FIFO 與 ADT。
2. 使用陣列實作 Stack 並處理 overflow、underflow。
3. 使用 Stack 完成反轉、括號配對與 Undo。
4. 使用 Queue 完成依序服務流程。
5. 說明 circular queue 的 front、rear、size 與環狀索引。
6. 使用 ArrayDeque 操作 Stack 與 Queue。
7. 根據資料取出順序選擇 Stack 或 Queue。

## 先備知識

- 陣列、ArrayList、Node、class 與 object。
- method、迴圈、條件判斷與 null。
- 建立 `0723` 資料夾，多檔案範例必須放在同一資料夾。

## 問題情境

有些資料必須先處理最近一筆，例如 Undo；有些資料必須依到達順序，例如叫號。Stack 與 Queue 用受限制的操作保證順序，讓程式不需要每次自行計算應取出哪一筆。

## 核心概念

### 概念 1：抽象資料型態 ADT 與操作規則

#### 概念說明

Stack 與 Queue 先定義允許的操作，再決定內部使用陣列、鏈結串列或 Java Collections。這種只對外公開操作規格、隱藏內部表示的方法稱為 Abstract Data Type。

呼叫端只需要知道 push、pop 或 offer、poll 的行為，不應直接修改內部陣列。相同 ADT 可以有不同實作，程式仍依相同操作規則使用。

#### 實際應用

- 模組化資料結構元件。
- 替換內部實作而不改呼叫端。
- 限制資料只能依規則進出。

#### 結構變化

Stack 只從同一端進出；Queue 從尾端加入、前端取出。

#### 何時適合使用

需要明確限制資料存取順序時。若需要任意索引讀取，應選其他結構。

#### 範例程式

檔名：`StackQueueComparison.java`

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class StackQueueComparison {
    public static void main(String[] args) {
        Deque<String> stack = new ArrayDeque<>();
        Deque<String> queue = new ArrayDeque<>();

        stack.push("A");
        stack.push("B");
        stack.push("C");

        queue.offer("A");
        queue.offer("B");
        queue.offer("C");

        System.out.println("Stack 先取出：" + stack.pop());
        System.out.println("Queue 先取出：" + queue.poll());
    }
}
```

執行結果：

```text
Stack 先取出：C
Queue 先取出：A
```

#### 執行重點

每次操作後都要確認頂端或前端資料、目前 size，以及空結構時的結果。不要只看集合印出的順序，要能說明下一次 pop 或 poll 會取得哪一筆。

---

### 概念 2：Stack、top 與 LIFO

#### 概念說明

Stack 採用 Last In, First Out。最後 push 的資料位於 top，也會最先被 pop。使用陣列實作時，top 保存目前頂端索引；空 Stack 設為 -1，第一筆資料放在索引 0。

push 先確認容量，再讓 top 增加並寫入；pop 先讀取 data[top]，再讓 top 減少。只調整 top 就能改變目前有效範圍。

#### 實際應用

- 復原上一個操作。
- 方法呼叫堆疊。
- 深度優先搜尋。

#### 結構變化

push 10、20、30 後 top 指向 30；pop 先取得 30，top 回到 20。

#### 何時適合使用

最近加入的資料必須最先處理時。需要依加入順序處理應使用 Queue。

#### 範例程式

檔名：`ArrayStack.java`

```java
public class ArrayStack {
    private int[] data;
    private int top;

    public ArrayStack(int capacity) {
        if (capacity <= 0) {
            capacity = 1;
        }
        data = new int[capacity];
        top = -1;
    }

    public boolean push(int value) {
        if (isFull()) {
            return false;
        }
        top++;
        data[top] = value;
        return true;
    }

    public Integer pop() {
        if (isEmpty()) {
            return null;
        }
        int value = data[top];
        top--;
        return value;
    }

    public Integer peek() {
        if (isEmpty()) {
            return null;
        }
        return data[top];
    }

    public int size() {
        return top + 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == data.length - 1;
    }

    public void print() {
        System.out.print("top [ ");
        for (int i = top; i >= 0; i--) {
            System.out.print(data[i] + " ");
        }
        System.out.println("] bottom");
    }
}
```

執行結果：

```text
此檔案是資料類別或資料結構類別，由同一概念的 Demo 程式建立並呼叫，不單獨執行。
```

檔名：`ArrayStackDemo.java`

```java
public class ArrayStackDemo {
    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(3);

        System.out.println("push 10：" + stack.push(10));
        System.out.println("push 20：" + stack.push(20));
        System.out.println("push 30：" + stack.push(30));
        System.out.println("push 40：" + stack.push(40));
        stack.print();

        System.out.println("peek：" + stack.peek());
        System.out.println("pop：" + stack.pop());
        stack.print();
        System.out.println("size：" + stack.size());
    }
}
```

執行結果：

```text
push 10：true
push 20：true
push 30：true
push 40：false
top [ 30 20 10 ] bottom
peek：30
pop：30
top [ 20 10 ] bottom
size：2
```

#### 執行重點

每次操作後都要確認頂端或前端資料、目前 size，以及空結構時的結果。不要只看集合印出的順序，要能說明下一次 pop 或 poll 會取得哪一筆。

---

### 概念 3：overflow、underflow 與空結構操作

#### 概念說明

固定容量陣列 Stack 已滿時繼續 push 稱為 overflow；空 Stack 執行 pop 或 peek 稱為 underflow。資料結構必須定義這些操作的結果，不能讓索引直接超出陣列。

本教材的 push 以 boolean 表示成功，pop 與 peek 在沒有資料時回傳 null。使用 Integer 而非 int，才能用 null 表示沒有結果。

#### 實際應用

- 固定容量的操作紀錄。
- 嵌入式系統的有限記憶體。
- 輸入錯誤或空資料防護。

#### 結構變化

容量 2 的 Stack 放入兩筆後 isFull 為 true；全部 pop 後 isEmpty 為 true。

#### 何時適合使用

容量有限且必須清楚處理失敗時。不要忽略 push 的回傳結果。

#### 範例程式

檔名：`StackBoundaryDemo.java`

```java
public class StackBoundaryDemo {
    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(2);

        System.out.println(stack.push(10));
        System.out.println(stack.push(20));
        System.out.println("overflow：" + stack.push(30));

        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println("underflow：" + stack.pop());
        System.out.println("empty peek：" + stack.peek());
    }
}
```

執行結果：

```text
true
true
overflow：false
20
10
underflow：null
empty peek：null
```

#### 執行重點

每次操作後都要確認頂端或前端資料、目前 size，以及空結構時的結果。不要只看集合印出的順序，要能說明下一次 pop 或 poll 會取得哪一筆。

---

### 概念 4：使用 ArrayDeque 操作 Stack

#### 概念說明

實際 Java 程式通常使用 `Deque` 介面搭配 `ArrayDeque`，而不是舊的 `Stack` 類別。作為 Stack 時使用 push、pop、peek；ArrayDeque 會自行管理容量。

手動實作能理解 top 與陣列，ArrayDeque 則適合正式應用。兩者操作概念相同，但 ArrayDeque 不接受 null 元素。

#### 實際應用

- Undo 記錄。
- 路徑回溯。
- 暫存尚未完成的巢狀工作。

#### 結構變化

每次 push 都把資料放到頂端；迭代輸出時最上方資料會先出現。

#### 何時適合使用

正式 Java 程式需要 Stack 行為時，優先使用 `Deque<T>` 與 `ArrayDeque<T>`。

#### 範例程式

檔名：`ArrayDequeStackDemo.java`

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class ArrayDequeStackDemo {
    public static void main(String[] args) {
        Deque<String> history = new ArrayDeque<>();

        history.push("Open file");
        history.push("Type title");
        history.push("Delete line");

        System.out.println("最近操作：" + history.peek());
        System.out.println("復原：" + history.pop());
        System.out.println("復原後頂端：" + history.peek());
        System.out.println("剩餘紀錄：" + history);
    }
}
```

執行結果：

```text
最近操作：Delete line
復原：Delete line
復原後頂端：Type title
剩餘紀錄：[Type title, Open file]
```

#### 執行重點

每次操作後都要確認頂端或前端資料、目前 size，以及空結構時的結果。不要只看集合印出的順序，要能說明下一次 pop 或 poll 會取得哪一筆。

---

### 概念 5：使用 Stack 反轉資料

#### 概念說明

將資料依原順序 push 進 Stack，再逐筆 pop，就會得到相反順序。這是 LIFO 最直接的應用，也能用來說明 Stack 不是排序，而是改變取出順序。

字串反轉可以逐字 push。若只需要反轉字串，StringBuilder 也有 reverse，但 Stack 版本能清楚呈現資料進出流程。

#### 實際應用

- 反向輸出紀錄。
- 回溯路徑。
- 理解遞迴返回順序。

#### 結構變化

依序 push J、a、v、a，pop 順序為 a、v、a、J。

#### 何時適合使用

需要暫存資料後反向取出時。只做簡單字串處理可選更直接 API。

#### 範例程式

檔名：`TextReverseStack.java`

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class TextReverseStack {
    public static void main(String[] args) {
        String text = "Data Structure";
        System.out.println("原文字：" + text);
        System.out.println("反轉後：" + reverse(text));
    }

    public static String reverse(String text) {
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < text.length(); i++) {
            stack.push(text.charAt(i));
        }

        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        return result.toString();
    }
}
```

執行結果：

```text
原文字：Data Structure
反轉後：erutcurtS ataD
```

#### 執行重點

每次操作後都要確認頂端或前端資料、目前 size，以及空結構時的結果。不要只看集合印出的順序，要能說明下一次 pop 或 poll 會取得哪一筆。

---

### 概念 6：使用 Stack 檢查括號配對

#### 概念說明

讀到左括號時 push；讀到右括號時，必須確認 Stack 不為空，而且頂端左括號類型相符。全部文字處理完成後，Stack 也必須為空，才代表沒有尚未關閉的括號。

這個流程和編譯器處理巢狀結構的概念相似。Stack 保存尚未配對的最近左括號，因此能正確處理多層巢狀。

#### 實際應用

- 程式碼語法檢查。
- 運算式解析。
- HTML 或設定檔的巢狀結構。

#### 結構變化

讀到 `([` 後 Stack 頂端是 `[`, 下一個 `]` 必須先配對它，再處理 `)`。

#### 何時適合使用

結構具有巢狀且最近開啟者必須先關閉時。

#### 範例程式

檔名：`BracketChecker.java`

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class BracketChecker {
    public static void main(String[] args) {
        System.out.println("([]{})：" + isBalanced("([]{})"));
        System.out.println("([)]：" + isBalanced("([)]"));
        System.out.println("((：" + isBalanced("(("));
    }

    public static boolean isBalanced(String text) {
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            } else if (ch == ')' || ch == ']' || ch == '}') {
                if (stack.isEmpty() || !matches(stack.pop(), ch)) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static boolean matches(char left, char right) {
        return (left == '(' && right == ')')
            || (left == '[' && right == ']')
            || (left == '{' && right == '}');
    }
}
```

執行結果：

```text
([]{})：true
([)]：false
((：false
```

#### 執行重點

每次操作後都要確認頂端或前端資料、目前 size，以及空結構時的結果。不要只看集合印出的順序，要能說明下一次 pop 或 poll 會取得哪一筆。

---

### 概念 7：Undo：保存操作前狀態

#### 概念說明

Undo 的基本做法是在修改前把目前狀態 push 進 Stack。執行 undo 時 pop 出最近保存的狀態並恢復。若 Stack 為空，代表沒有可復原操作。

大型編輯器可能保存 command 或差異，而不是複製全部文字；本範例先保存完整字串，重點是理解最近一次操作最先復原。

#### 實際應用

- 文字編輯器復原。
- 繪圖軟體撤銷。
- 表單修改回復。

#### 結構變化

文字 A 修改為 AB 前先保存 A；再改 ABC 前保存 AB；undo 先回到 AB。

#### 何時適合使用

操作需要依相反順序復原時。若要 redo，還需要第二個 Stack。

#### 範例程式

檔名：`UndoManager.java`

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class UndoManager {
    private String text = "";
    private Deque<String> history = new ArrayDeque<>();

    public void append(String value) {
        history.push(text);
        text += value;
    }

    public boolean undo() {
        if (history.isEmpty()) {
            return false;
        }
        text = history.pop();
        return true;
    }

    public String getText() {
        return text;
    }

    public static void main(String[] args) {
        UndoManager editor = new UndoManager();
        editor.append("Java");
        editor.append(" Stack");
        editor.append(" Demo");
        System.out.println(editor.getText());

        editor.undo();
        System.out.println(editor.getText());
        editor.undo();
        System.out.println(editor.getText());
    }
}
```

執行結果：

```text
Java Stack Demo
Java Stack
Java
```

#### 執行重點

每次操作後都要確認頂端或前端資料、目前 size，以及空結構時的結果。不要只看集合印出的順序，要能說明下一次 pop 或 poll 會取得哪一筆。

---

### 概念 8：Queue、front、rear 與 FIFO

#### 概念說明

Queue 採用 First In, First Out。offer 從 rear 加入，poll 從 front 取出，peek 只查看 front。最早進入的資料最先離開，適合公平排隊與依到達順序處理。

front 與 rear 代表不同端。若把資料從相同端加入與取出，就會變成 Stack 行為。操作名稱能提醒程式目前使用的是哪一種規則。

#### 實際應用

- 掛號叫號。
- 印表機工作。
- 背景任務與訊息處理。

#### 結構變化

offer A、B、C 後，front 是 A；poll 取得 A，新的 front 是 B。

#### 何時適合使用

資料必須依到達順序處理時。需要優先級時應使用 PriorityQueue。

#### 範例程式

檔名：`QueueOperationDemo.java`

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class QueueOperationDemo {
    public static void main(String[] args) {
        Deque<String> queue = new ArrayDeque<>();

        queue.offer("A001");
        queue.offer("A002");
        queue.offer("A003");

        System.out.println("下一位：" + queue.peek());
        System.out.println("叫號：" + queue.poll());
        System.out.println("新的下一位：" + queue.peek());
        System.out.println("等待人數：" + queue.size());
    }
}
```

執行結果：

```text
下一位：A001
叫號：A001
新的下一位：A002
等待人數：2
```

#### 執行重點

每次操作後都要確認頂端或前端資料、目前 size，以及空結構時的結果。不要只看集合印出的順序，要能說明下一次 pop 或 poll 會取得哪一筆。

---

### 概念 9：Circular Queue 與陣列空間重複使用

#### 概念說明

一般陣列 Queue 若 rear 只往右移，即使前方資料已被取出，也可能到達陣列尾端而無法再加入。Circular Queue 使用 `% capacity` 讓 front 與 rear 到達尾端後回到索引 0。

只看 front 與 rear 可能無法分辨空與滿，因此另外保存 size。offer 寫入 rear 後移動 rear；poll 讀取 front 後移動 front。

#### 實際應用

- 影音串流緩衝區。
- 固定容量感測資料。
- 網路封包與生產者消費者 buffer。

#### 結構變化

容量 4 的 rear 位於索引 3，加入後以 `(3 + 1) % 4` 回到索引 0。

#### 何時適合使用

容量固定且需要重複使用陣列位置時。容量動態且不需理解底層時使用 ArrayDeque。

#### 範例程式

檔名：`CircularQueue.java`

```java
public class CircularQueue {
    private int[] data;
    private int front;
    private int rear;
    private int size;

    public CircularQueue(int capacity) {
        if (capacity <= 0) {
            capacity = 1;
        }
        data = new int[capacity];
        front = 0;
        rear = 0;
        size = 0;
    }

    public boolean offer(int value) {
        if (isFull()) {
            return false;
        }
        data[rear] = value;
        rear = (rear + 1) % data.length;
        size++;
        return true;
    }

    public Integer poll() {
        if (isEmpty()) {
            return null;
        }
        int value = data[front];
        front = (front + 1) % data.length;
        size--;
        return value;
    }

    public Integer peek() {
        if (isEmpty()) {
            return null;
        }
        return data[front];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == data.length;
    }

    public void print() {
        System.out.print("front [ ");
        for (int i = 0; i < size; i++) {
            int index = (front + i) % data.length;
            System.out.print(data[index] + " ");
        }
        System.out.println("] rear");
    }
}
```

執行結果：

```text
此檔案是資料類別或資料結構類別，由同一概念的 Demo 程式建立並呼叫，不單獨執行。
```

檔名：`CircularQueueDemo.java`

```java
public class CircularQueueDemo {
    public static void main(String[] args) {
        CircularQueue queue = new CircularQueue(4);

        queue.offer(10);
        queue.offer(20);
        queue.offer(30);
        queue.print();

        System.out.println("poll：" + queue.poll());
        System.out.println("poll：" + queue.poll());
        queue.offer(40);
        queue.offer(50);
        queue.offer(60);
        queue.print();

        System.out.println("peek：" + queue.peek());
        System.out.println("full：" + queue.isFull());
    }
}
```

執行結果：

```text
front [ 10 20 30 ] rear
poll：10
poll：20
front [ 30 40 50 60 ] rear
peek：30
full：true
```

#### 執行重點

每次操作後都要確認頂端或前端資料、目前 size，以及空結構時的結果。不要只看集合印出的順序，要能說明下一次 pop 或 poll 會取得哪一筆。

---

### 概念 10：使用 ArrayDeque 操作 Queue

#### 概念說明

Java 中可用 `Deque<T>` 搭配 `ArrayDeque<T>` 實作 Queue。offer 加入尾端，poll 移除前端，peek 查看前端。這些方法在空 Queue 時回傳 null，比直接發生例外更適合一般流程判斷。

不要在同一段程式混用 push/pop 與 offer/poll，否則資料進出方向會變得難以判斷。變數名稱與 method 選擇都應明確表達目前使用 Queue。

#### 實際應用

- 客服等待名單。
- 檔案上傳工作。
- 訂單逐筆處理。

#### 結構變化

每次 poll 後等待人數減少，後方資料依原順序向前成為下一筆。

#### 何時適合使用

正式 Java 程式需要一般 Queue 時，優先使用 ArrayDeque。

#### 範例程式

檔名：`ArrayDequeQueueDemo.java`

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class ArrayDequeQueueDemo {
    public static void main(String[] args) {
        Deque<String> printJobs = new ArrayDeque<>();

        printJobs.offer("report.pdf");
        printJobs.offer("photo.png");
        printJobs.offer("slides.pptx");

        while (!printJobs.isEmpty()) {
            String job = printJobs.poll();
            System.out.println("列印：" + job);
        }

        System.out.println("剩餘工作：" + printJobs.size());
    }
}
```

執行結果：

```text
列印：report.pdf
列印：photo.png
列印：slides.pptx
剩餘工作：0
```

#### 執行重點

每次操作後都要確認頂端或前端資料、目前 size，以及空結構時的結果。不要只看集合印出的順序，要能說明下一次 pop 或 poll 會取得哪一筆。

---

### 概念 11：Queue 服務流程模擬

#### 概念說明

服務系統接收資料時 offer，櫃台完成一筆時 poll。Queue 本身只負責順序，服務時間、完成狀態與統計應由其他變數或物件管理。

模擬時要處理沒有等待資料卻要求叫號的情況，也要能顯示下一筆與目前等待數量。

#### 實際應用

- 診所掛號。
- 銀行叫號。
- 客服工單與維修排程。

#### 結構變化

三人排隊後 poll 第一人，剩餘兩人的相對順序不變。

#### 何時適合使用

每筆工作依到達時間公平處理時。緊急程度不同則需要另外定義優先規則。

#### 範例程式

檔名：`ServiceQueueSimulation.java`

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class ServiceQueueSimulation {
    public static void main(String[] args) {
        Deque<String> waiting = new ArrayDeque<>();
        waiting.offer("A001 Amy");
        waiting.offer("A002 Ben");
        waiting.offer("A003 Cara");

        serveNext(waiting);
        serveNext(waiting);
        System.out.println("下一位：" + waiting.peek());
        System.out.println("等待人數：" + waiting.size());
    }

    public static void serveNext(Deque<String> waiting) {
        String customer = waiting.poll();
        if (customer == null) {
            System.out.println("目前無人等待");
        } else {
            System.out.println("服務：" + customer);
        }
    }
}
```

執行結果：

```text
服務：A001 Amy
服務：A002 Ben
下一位：A003 Cara
等待人數：1
```

#### 執行重點

每次操作後都要確認頂端或前端資料、目前 size，以及空結構時的結果。不要只看集合印出的順序，要能說明下一次 pop 或 poll 會取得哪一筆。

---

### 概念 12：整合 Queue 與 Stack

#### 概念說明

一個系統可以同時使用多種資料結構。Queue 保存尚未處理的工作，確保依序完成；Stack 保存已完成紀錄，讓最近完成的工作可以被復原。

選擇結構時要針對每一組資料的操作需求，而不是整個系統只能選一種。等待與歷史紀錄具有不同順序，因此需要不同結構。

#### 實際應用

- 配送工作與取消完成。
- 列印佇列與重新執行。
- 工作流程與 Undo。

#### 結構變化

process：waiting.poll 後 push 到 completed；undo：completed.pop 後再 offer 回 waiting。

#### 何時適合使用

系統同時存在依序處理與反向復原需求時。

#### 範例程式

檔名：`DeliveryFlowDemo.java`

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class DeliveryFlowDemo {
    public static void main(String[] args) {
        Deque<String> waiting = new ArrayDeque<>();
        Deque<String> completed = new ArrayDeque<>();

        waiting.offer("D001");
        waiting.offer("D002");
        waiting.offer("D003");

        processNext(waiting, completed);
        processNext(waiting, completed);
        undoLast(waiting, completed);

        System.out.println("等待：" + waiting);
        System.out.println("完成紀錄：" + completed);
    }

    public static void processNext(
        Deque<String> waiting,
        Deque<String> completed
    ) {
        String task = waiting.poll();
        if (task != null) {
            completed.push(task);
        }
    }

    public static void undoLast(
        Deque<String> waiting,
        Deque<String> completed
    ) {
        String task = completed.poll();
        if (task != null) {
            waiting.offer(task);
        }
    }
}
```

執行結果：

```text
等待：[D003, D002]
完成紀錄：[D001]
```

#### 執行重點

每次操作後都要確認頂端或前端資料、目前 size，以及空結構時的結果。不要只看集合印出的順序，要能說明下一次 pop 或 poll 會取得哪一筆。

---

### 概念 13：判斷應使用 Stack 或 Queue

#### 概念說明

若問題反覆出現「最近一次」、「上一個」、「巢狀關閉」或「回溯」，通常適合 Stack。若出現「先到先處理」、「排隊」、「等待工作」或「依序消費」，通常適合 Queue。

資料結構選擇應從取出順序開始，再考慮容量、是否需要搜尋及是否需要保留所有歷史。選錯結構常導致額外搬移或複雜判斷。

#### 實際應用

- 上一頁使用 Stack。
- 客服等待使用 Queue。
- BFS 使用 Queue、DFS 可使用 Stack。

#### 結構變化

相同 A、B、C 輸入，Stack 輸出 C、B、A；Queue 輸出 A、B、C。

#### 何時適合使用

先寫出資料進入與取出的順序範例，再決定資料結構。

#### 範例程式

檔名：`StackQueueDecisionDemo.java`

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class StackQueueDecisionDemo {
    public static void main(String[] args) {
        Deque<String> backHistory = new ArrayDeque<>();
        backHistory.push("Home");
        backHistory.push("Courses");
        backHistory.push("Java");

        Deque<String> uploadQueue = new ArrayDeque<>();
        uploadQueue.offer("fileA.txt");
        uploadQueue.offer("fileB.txt");
        uploadQueue.offer("fileC.txt");

        System.out.println("返回上一頁：" + backHistory.pop());
        System.out.println("先上傳：" + uploadQueue.poll());
    }
}
```

執行結果：

```text
返回上一頁：Java
先上傳：fileA.txt
```

#### 執行重點

每次操作後都要確認頂端或前端資料、目前 size，以及空結構時的結果。不要只看集合印出的順序，要能說明下一次 pop 或 poll 會取得哪一筆。

---

## 課堂實作題

### 課堂實作題一：瀏覽操作復原

建立以下檔案：

- `BrowserUndoSystem.java`

功能要求：

1. 使用 Stack 保存瀏覽頁面。
2. 支援開啟新頁、返回上一頁、查看目前頁面。
3. 沒有上一頁時顯示明確訊息。
4. 至少完成 8 次操作測試。

完成標準：資料進出順序正確、空結構操作安全、檔名與 public class 一致。

---

### 課堂實作題二：括號驗證

建立以下檔案：

- `BracketValidationSystem.java`

功能要求：

1. 支援小括號、中括號與大括號。
2. 忽略非括號字元。
3. 正確處理多層巢狀、順序錯誤、缺少左括號與缺少右括號。
4. 將配對判斷拆成 method。

完成標準：資料進出順序正確、空結構操作安全、檔名與 public class 一致。

---

### 課堂實作題三：櫃台叫號

建立以下檔案：

- `CounterServiceSystem.java`

功能要求：

1. 使用 Queue 保存號碼與姓名。
2. 支援取號、叫號、查看下一位與等待人數。
3. 空 Queue 叫號不可發生錯誤。
4. 處理紀錄要另外保存。

完成標準：資料進出順序正確、空結構操作安全、檔名與 public class 一致。


## 課後作業

以下三題全部放在 `0723` 資料夾，並依 iClass 當天作業區要求繳交 `0723` 資料夾的 GitHub 連結。

### 課後作業一：文字編輯 Undo

建立以下檔案：

- `TextEditorUndoSystem.java`

功能要求：

1. 支援新增文字、刪除最後數個字元、Undo 與顯示內容。
2. 每次修改前保存狀態。
3. 沒有歷史時 Undo 要顯示訊息。
4. 至少連續 Undo 三次並驗證結果。

完成標準：資料進出順序正確、空結構操作安全、檔名與 public class 一致。

---

### 課後作業二：診所叫號系統

建立以下檔案：

- `Patient.java`
- `ClinicQueueSystem.java`

功能要求：

1. Patient 包含號碼、姓名與科別。
2. 使用 Queue 支援掛號、叫號、查看下一位與等待清單。
3. 號碼不可重複。
4. 輸出各科別等待人數與總服務人數。

完成標準：資料進出順序正確、空結構操作安全、檔名與 public class 一致。

---

### 課後作業三：配送工作流程

建立以下檔案：

- `DeliveryTask.java`
- `DeliveryProcessingSystem.java`

功能要求：

1. Queue 保存待配送工作，Stack 保存完成紀錄。
2. 支援新增、完成下一筆、查看下一筆與復原最近完成。
3. 復原後工作回到等待 Queue 尾端。
4. 輸出等待數、完成數與所有處理紀錄。

完成標準：資料進出順序正確、空結構操作安全、檔名與 public class 一致。


## 常見錯誤與診斷

- Stack 把 top 初始值設為 0，造成空結構判斷錯誤。
- pop 或 poll 前沒有判斷是否為空。
- 固定容量結構沒有處理 overflow。
- Queue 混用 push/pop，導致變成 Stack 行為。
- circular queue 移動索引時忘記 `% capacity`。
- 只用 front 與 rear 判斷空或滿，卻沒有 size 或其他規則。
- 括號檢查只比較數量，沒有比較巢狀順序。
- Undo 在修改後才保存狀態，導致無法回到修改前。

## 評分規準

- LIFO、FIFO 與資料進出方向正確。
- overflow、underflow 與空結構處理完整。
- circular queue 能重複使用陣列位置。
- Stack 與 Queue 應用符合情境。
- 類別與 method 拆分清楚，所有檔案可共同編譯。
