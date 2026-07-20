# 7/21 教材：ArrayList 與動態資料管理

## 單元名稱

ArrayList、泛型、動態集合與物件 CRUD

## 課程定位

前面的陣列可以保存多筆資料，但長度在建立後固定。本單元改用 `ArrayList` 管理會增加、修改與刪除的資料，並把封裝過的物件放入集合，建立小型資料管理系統。

## 學習目標

1. 說明陣列與 ArrayList 在容量與操作上的差異。
2. 建立具有正確泛型的 ArrayList。
3. 完成新增、讀取、修改、刪除、走訪與搜尋。
4. 將集合傳入 method 或作為回傳值。
5. 使用 ArrayList 管理物件並完成 CRUD。
6. 根據主要操作選擇陣列或 ArrayList。

## 先備知識

- 陣列、索引、迴圈與 method。
- class、object、constructor、getter、setter 與 `toString()`。
- 建立 `0721` 資料夾，所有範例依標示檔名存放。

## 問題情境

報名人數、購物車商品與設備清單不會永遠固定。若每次都建立很大的陣列並自行管理有效筆數，新增與刪除容易出錯。ArrayList 將容量管理包裝起來，讓程式把注意力放在資料規則與操作流程。

## 核心概念

### 概念 1：陣列與動態集合的差異

#### 概念說明

一般陣列在建立時就必須決定長度，之後不能直接增加容量。當資料筆數固定，例如一週七天的溫度，陣列很合適；但報名名單、購物車或搜尋結果的筆數通常會持續改變，事先猜一個容量容易造成空間浪費或容量不足。

`ArrayList` 是可以動態增加與縮減的集合。它仍然依照索引保存資料，但新增與刪除時會由 Java 協助管理容量。這並不代表 `ArrayList` 永遠比陣列好，而是資料數量不固定時通常比較容易維護。

#### 實際應用

- 活動報名名單持續增加。
- 購物車商品可隨時加入或移除。
- 搜尋結果筆數事前無法確定。

#### 資料變化

陣列 `{10, 20, 30}` 的長度固定為 3；ArrayList 可由 `[10, 20]` 經過 `add(30)` 變成 `[10, 20, 30]`。

#### 何時適合使用

資料筆數會改變，且仍需要依索引讀取資料時。資料數量固定或需要處理 primitive 大量運算時，陣列通常更直接。

#### 範例程式

檔名：`ArrayVsArrayListDemo.java`

```java
import java.util.ArrayList;

public class ArrayVsArrayListDemo {
    public static void main(String[] args) {
        int[] fixedScores = new int[3];
        fixedScores[0] = 80;
        fixedScores[1] = 90;
        fixedScores[2] = 75;

        ArrayList<Integer> dynamicScores = new ArrayList<>();
        dynamicScores.add(80);
        dynamicScores.add(90);
        dynamicScores.add(75);
        dynamicScores.add(88);

        System.out.println("陣列長度：" + fixedScores.length);
        System.out.println("ArrayList 大小：" + dynamicScores.size());
        System.out.println("動態成績：" + dynamicScores);
    }
}
```

執行結果：

```text
陣列長度：3
ArrayList 大小：4
動態成績：[80, 90, 75, 88]
```

#### 執行重點

建立檔案後先確認檔名與 `public class` 一致，再觀察每次操作前後的集合內容與 `size()`。不要只看最後輸出，要能指出是哪一行讓資料增加、取代、移動或被刪除。

---

### 概念 2：建立 ArrayList 與泛型

#### 概念說明

使用 `ArrayList` 前要匯入 `java.util.ArrayList`。尖括號中的型態稱為 generic type，表示集合允許保存的資料類型，例如 `ArrayList<String>` 只能放字串，`ArrayList<Integer>` 保存整數物件。

泛型讓編譯器提早檢查型態，避免讀取資料後才發現型態不符。Java 的基本型態不能直接放進泛型，因此 `int` 對應 `Integer`、`double` 對應 `Double`、`boolean` 對應 `Boolean`。Java 會自動完成多數裝箱與拆箱操作。

#### 實際應用

- 姓名清單使用 String。
- 分數清單使用 Integer。
- 商品集合使用 Product。

#### 資料變化

建立時集合為 `[]`，尚未包含資料；宣告的泛型決定之後可以加入的型態。

#### 何時適合使用

需要同一集合只保存同一類型資料時。不要使用沒有泛型的 raw type，否則容易失去型態檢查。

#### 範例程式

檔名：`ArrayListCreateDemo.java`

```java
import java.util.ArrayList;

public class ArrayListCreateDemo {
    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> scores = new ArrayList<>();
        ArrayList<Double> prices = new ArrayList<>();

        System.out.println("names = " + names);
        System.out.println("scores = " + scores);
        System.out.println("prices = " + prices);
    }
}
```

執行結果：

```text
names = []
scores = []
prices = []
```

#### 執行重點

建立檔案後先確認檔名與 `public class` 一致，再觀察每次操作前後的集合內容與 `size()`。不要只看最後輸出，要能指出是哪一行讓資料增加、取代、移動或被刪除。

---

### 概念 3：使用 add() 新增資料

#### 概念說明

`add(value)` 會把資料加入集合尾端；`add(index, value)` 則會插入指定位置，原本位於該位置及後方的資料會往右移動。每次新增後，`size()` 都會隨之增加。

插入位置必須介於 0 與目前的 `size()` 之間。當集合有 3 筆資料時，可以插入索引 0、1、2、3，但不能直接插入索引 4。索引 3 代表加到尾端。

#### 實際應用

- 加入購物車項目。
- 新增報名資料。
- 在播放清單指定位置插入歌曲。

#### 資料變化

`[Java, Python]` 執行 `add(1, "SQL")` 後變成 `[Java, SQL, Python]`。

#### 何時適合使用

一般新增使用尾端 `add(value)`；只有確定位置有意義時才使用索引插入。

#### 範例程式

檔名：`ArrayListAddDemo.java`

```java
import java.util.ArrayList;

public class ArrayListAddDemo {
    public static void main(String[] args) {
        ArrayList<String> courses = new ArrayList<>();

        courses.add("Java");
        courses.add("Python");
        courses.add(1, "SQL");

        System.out.println("課程數量：" + courses.size());
        System.out.println("課程清單：" + courses);
    }
}
```

執行結果：

```text
課程數量：3
課程清單：[Java, SQL, Python]
```

#### 執行重點

建立檔案後先確認檔名與 `public class` 一致，再觀察每次操作前後的集合內容與 `size()`。不要只看最後輸出，要能指出是哪一行讓資料增加、取代、移動或被刪除。

---

### 概念 4：使用 size()、get() 與 set()

#### 概念說明

`size()` 回傳目前資料筆數，合法索引為 0 到 `size() - 1`。`get(index)` 讀取指定位置，`set(index, value)` 則用新資料取代原位置內容，但不會改變集合大小。

`set()` 是修改，不是新增。如果要在空集合放入第一筆資料，必須使用 `add()`；對空集合執行 `set(0, value)` 會發生 `IndexOutOfBoundsException`。

#### 實際應用

- 讀取指定座位的登記資料。
- 修改購物車商品數量。
- 更新課程名稱。

#### 資料變化

`[80, 75, 90]` 執行 `set(1, 85)` 後變成 `[80, 85, 90]`，大小仍是 3。

#### 何時適合使用

資料已存在而且知道索引時使用。若只知道內容不知道位置，應先搜尋。

#### 範例程式

檔名：`ArrayListReadUpdateDemo.java`

```java
import java.util.ArrayList;

public class ArrayListReadUpdateDemo {
    public static void main(String[] args) {
        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(80);
        scores.add(75);
        scores.add(90);

        System.out.println("修改前第 2 筆：" + scores.get(1));
        scores.set(1, 85);
        System.out.println("修改後第 2 筆：" + scores.get(1));
        System.out.println("資料筆數：" + scores.size());
        System.out.println("全部成績：" + scores);
    }
}
```

執行結果：

```text
修改前第 2 筆：75
修改後第 2 筆：85
資料筆數：3
全部成績：[80, 85, 90]
```

#### 執行重點

建立檔案後先確認檔名與 `public class` 一致，再觀察每次操作前後的集合內容與 `size()`。不要只看最後輸出，要能指出是哪一行讓資料增加、取代、移動或被刪除。

---

### 概念 5：使用 remove() 刪除資料

#### 概念說明

`remove(index)` 依索引刪除資料，刪除後右側資料會向左補位。對物件集合也能使用 `remove(object)` 依內容移除第一筆相同資料。

`ArrayList<Integer>` 特別容易混淆：`remove(1)` 會把 1 當成索引；若要刪除整數值 1，可使用 `remove(Integer.valueOf(1))`。每次刪除後都要重新理解索引位置。

#### 實際應用

- 取消報名。
- 移除購物車商品。
- 刪除過期通知。

#### 資料變化

`[Amy, Ben, Cara]` 刪除索引 1 後變成 `[Amy, Cara]`，Cara 的索引由 2 變成 1。

#### 何時適合使用

知道索引或能準確辨識目標物件時。大量從集合前端刪除時，ArrayList 搬移資料的成本較高。

#### 範例程式

檔名：`ArrayListRemoveDemo.java`

```java
import java.util.ArrayList;

public class ArrayListRemoveDemo {
    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<>();
        names.add("Amy");
        names.add("Ben");
        names.add("Cara");

        String removed = names.remove(1);
        boolean foundAndRemoved = names.remove("Amy");

        System.out.println("依索引刪除：" + removed);
        System.out.println("依內容刪除成功：" + foundAndRemoved);
        System.out.println("剩餘名單：" + names);
    }
}
```

執行結果：

```text
依索引刪除：Ben
依內容刪除成功：true
剩餘名單：[Cara]
```

#### 執行重點

建立檔案後先確認檔名與 `public class` 一致，再觀察每次操作前後的集合內容與 `size()`。不要只看最後輸出，要能指出是哪一行讓資料增加、取代、移動或被刪除。

---

### 概念 6：使用 contains() 與 indexOf() 搜尋

#### 概念說明

`contains(value)` 回傳是否存在，適合只關心「有沒有」；`indexOf(value)` 回傳第一次出現的索引，找不到時回傳 -1。兩者都會從前往後逐筆比較。

字串內容比較會使用 `equals()`。自訂物件若沒有覆寫 `equals()`，即使欄位內容相同，也不一定會被判定為相同物件，因此物件集合常以迴圈搭配唯一代碼搜尋。

#### 實際應用

- 檢查課程是否重複加入。
- 找出姓名所在位置。
- 用商品代碼搜尋物件。

#### 資料變化

搜尋不改變集合；`indexOf("SQL")` 只回傳位置，找不到得到 -1。

#### 何時適合使用

資料量不大而且只需要循序搜尋時。大量依唯一鍵查詢時，後續可改用 Map。

#### 範例程式

檔名：`ArrayListSearchDemo.java`

```java
import java.util.ArrayList;

public class ArrayListSearchDemo {
    public static void main(String[] args) {
        ArrayList<String> courses = new ArrayList<>();
        courses.add("Java");
        courses.add("SQL");
        courses.add("Web");

        String keyword = "SQL";
        boolean exists = courses.contains(keyword);
        int index = courses.indexOf(keyword);

        System.out.println("是否存在：" + exists);
        System.out.println("所在索引：" + index);
        System.out.println("找不到的結果：" + courses.indexOf("Python"));
    }
}
```

執行結果：

```text
是否存在：true
所在索引：1
找不到的結果：-1
```

#### 執行重點

建立檔案後先確認檔名與 `public class` 一致，再觀察每次操作前後的集合內容與 `size()`。不要只看最後輸出，要能指出是哪一行讓資料增加、取代、移動或被刪除。

---

### 概念 7：走訪 ArrayList

#### 概念說明

需要索引時使用一般 `for`，例如輸出編號或修改指定位置；只需要逐筆讀取時，`for-each` 較簡潔。兩種方式都能做總和、平均、條件計數與搜尋。

`for-each` 中的變數代表目前資料，不是索引。也不應在 `for-each` 中直接呼叫集合的 `remove()`，否則可能發生 `ConcurrentModificationException`。

#### 實際應用

- 列出有編號的課程。
- 計算購物車總額。
- 找出超過門檻的成績。

#### 資料變化

走訪本身不改變集合；若使用索引配合 `set()`，才會更新原集合。

#### 何時適合使用

需要位置用 `for`；單純讀取用 `for-each`。需要刪除時改用倒序索引或其他安全方式。

#### 範例程式

檔名：`ArrayListTraversalDemo.java`

```java
import java.util.ArrayList;

public class ArrayListTraversalDemo {
    public static void main(String[] args) {
        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(80);
        scores.add(92);
        scores.add(76);

        for (int i = 0; i < scores.size(); i++) {
            System.out.println("索引 " + i + "：" + scores.get(i));
        }

        int total = 0;
        for (int score : scores) {
            total += score;
        }

        System.out.println("總分：" + total);
        System.out.println("平均：" + (double) total / scores.size());
    }
}
```

執行結果：

```text
索引 0：80
索引 1：92
索引 2：76
總分：248
平均：82.66666666666667
```

#### 執行重點

建立檔案後先確認檔名與 `public class` 一致，再觀察每次操作前後的集合內容與 `size()`。不要只看最後輸出，要能指出是哪一行讓資料增加、取代、移動或被刪除。

---

### 概念 8：ArrayList 作為 method 參數與回傳值

#### 概念說明

將集合傳入 method，可以把統計、搜尋與篩選功能從 `main()` 拆開。method 接收到的是集合物件的參考，因此直接 `add()`、`set()` 或 `remove()` 會影響原集合。

若不希望修改原資料，可以建立新的 `ArrayList`，把符合條件的資料加入後回傳。這種做法讓「原始資料」與「篩選結果」分開，較容易測試與追蹤。

#### 實際應用

- 篩選及格成績。
- 回傳符合關鍵字的商品。
- 將統計功能拆成獨立 method。

#### 資料變化

原集合 `[55, 80, 92]` 經過篩選後仍保持不變，新的結果集合是 `[80, 92]`。

#### 何時適合使用

希望重複使用搜尋或統計邏輯時。若 method 會修改原集合，名稱與說明要明確。

#### 範例程式

檔名：`ArrayListMethodDemo.java`

```java
import java.util.ArrayList;

public class ArrayListMethodDemo {
    public static void main(String[] args) {
        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(55);
        scores.add(80);
        scores.add(92);

        ArrayList<Integer> passed = filterAtLeast(scores, 60);
        System.out.println("原始資料：" + scores);
        System.out.println("及格資料：" + passed);
    }

    public static ArrayList<Integer> filterAtLeast(
        ArrayList<Integer> values,
        int target
    ) {
        ArrayList<Integer> result = new ArrayList<>();

        for (int value : values) {
            if (value >= target) {
                result.add(value);
            }
        }

        return result;
    }
}
```

執行結果：

```text
原始資料：[55, 80, 92]
及格資料：[80, 92]
```

#### 執行重點

建立檔案後先確認檔名與 `public class` 一致，再觀察每次操作前後的集合內容與 `size()`。不要只看最後輸出，要能指出是哪一行讓資料增加、取代、移動或被刪除。

---

### 概念 9：走訪過程中的安全刪除

#### 概念說明

從前往後刪除時，後方資料會左移，迴圈索引若照常增加，就可能跳過剛移到目前位置的資料。最容易理解的做法是從最後一個索引往前檢查，刪除後不會影響尚未處理的前方索引。

另一種做法是使用 `removeIf()`，但在學習資料結構時仍要理解索引移動的原因。安全刪除不是記住一段語法，而是掌握結構改變後哪些位置會受到影響。

#### 實際應用

- 刪除所有不及格成績。
- 清除庫存為零商品。
- 移除過期通知。

#### 資料變化

倒序處理 `[40, 80, 50, 90]`，刪除小於 60 的資料後得到 `[80, 90]`。

#### 何時適合使用

需要依條件刪除多筆資料時。若只刪一筆，可先搜尋索引再刪除。

#### 範例程式

檔名：`SafeRemoveDemo.java`

```java
import java.util.ArrayList;

public class SafeRemoveDemo {
    public static void main(String[] args) {
        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(40);
        scores.add(80);
        scores.add(50);
        scores.add(90);

        for (int i = scores.size() - 1; i >= 0; i--) {
            if (scores.get(i) < 60) {
                scores.remove(i);
            }
        }

        System.out.println("保留的成績：" + scores);
    }
}
```

執行結果：

```text
保留的成績：[80, 90]
```

#### 執行重點

建立檔案後先確認檔名與 `public class` 一致，再觀察每次操作前後的集合內容與 `size()`。不要只看最後輸出，要能指出是哪一行讓資料增加、取代、移動或被刪除。

---

### 概念 10：使用 ArrayList 管理物件

#### 概念說明

實際系統通常不是只保存數字或字串，而是保存商品、課程、設備等物件。`ArrayList<Product>` 中每個元素都是 `Product` 物件參考，可以透過 getter 讀取欄位，也能呼叫物件的方法更新狀態。

搜尋物件時應選擇穩定且唯一的欄位，例如商品代碼。不要只比較顯示名稱，因為名稱可能重複。找到物件後，可以直接呼叫 setter 或業務方法修改同一個物件。

#### 實際應用

- 商品庫存管理。
- 設備借用清單。
- 課程與選課資料。

#### 資料變化

集合保存的是物件參考；修改找到的 Product，集合中看到的內容也會更新。

#### 何時適合使用

每筆資料具有多個欄位與操作規則時。若只有單一數值，使用基本型態集合即可。

#### 範例程式

檔名：`Product.java`

```java
public class Product {
    private String code;
    private String name;
    private int stock;

    public Product(String code, String name, int stock) {
        this.code = code;
        this.name = name;
        this.stock = Math.max(stock, 0);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public void addStock(int amount) {
        if (amount > 0) {
            stock += amount;
        }
    }

    @Override
    public String toString() {
        return code + " | " + name + " | stock=" + stock;
    }
}
```

執行結果：

```text
此檔案是資料類別或資料結構類別，由同一概念的 Demo 程式建立並呼叫，不單獨執行。
```

檔名：`ProductArrayListDemo.java`

```java
import java.util.ArrayList;

public class ProductArrayListDemo {
    public static void main(String[] args) {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("P01", "Keyboard", 10));
        products.add(new Product("P02", "Mouse", 6));
        products.add(new Product("P03", "Monitor", 3));

        Product found = findByCode(products, "P02");
        if (found != null) {
            found.addStock(4);
        }

        for (Product product : products) {
            System.out.println(product);
        }
    }

    public static Product findByCode(
        ArrayList<Product> products,
        String code
    ) {
        for (Product product : products) {
            if (product.getCode().equalsIgnoreCase(code)) {
                return product;
            }
        }
        return null;
    }
}
```

執行結果：

```text
P01 | Keyboard | stock=10
P02 | Mouse | stock=10
P03 | Monitor | stock=3
```

#### 執行重點

建立檔案後先確認檔名與 `public class` 一致，再觀察每次操作前後的集合內容與 `size()`。不要只看最後輸出，要能指出是哪一行讓資料增加、取代、移動或被刪除。

---

### 概念 11：以 ArrayList 完成 CRUD

#### 概念說明

CRUD 代表 Create、Read、Update、Delete，是資料管理系統最常見的四種操作。Create 將物件加入集合，Read 走訪或搜尋，Update 找到物件後修改狀態，Delete 則移除指定物件。

CRUD 系統的關鍵不是選單本身，而是把每個操作拆成清楚的 method，統一處理「找不到」、「代碼重複」與「索引改變」等情況。main 應負責流程，資料操作交給對應方法。

#### 實際應用

- 選課管理。
- 聯絡人管理。
- 設備與庫存管理。

#### 資料變化

新增會使 size 增加，修改不改變 size，刪除會使 size 減少並重排後方索引。

#### 何時適合使用

小型記憶體資料管理工具。需要永久保存或多人共用時，後續應改用檔案或資料庫。

#### 範例程式

檔名：`Course.java`

```java
public class Course {
    private String code;
    private String name;
    private int enrolled;

    public Course(String code, String name) {
        this.code = code;
        this.name = name;
        this.enrolled = 0;
    }

    public String getCode() {
        return code;
    }

    public void enroll() {
        enrolled++;
    }

    @Override
    public String toString() {
        return code + " | " + name + " | enrolled=" + enrolled;
    }
}
```

執行結果：

```text
此檔案是資料類別或資料結構類別，由同一概念的 Demo 程式建立並呼叫，不單獨執行。
```

檔名：`CourseCrudSystem.java`

```java
import java.util.ArrayList;

public class CourseCrudSystem {
    public static void main(String[] args) {
        ArrayList<Course> courses = new ArrayList<>();

        addCourse(courses, new Course("C01", "Java"));
        addCourse(courses, new Course("C02", "Data Structures"));

        Course found = findCourse(courses, "C02");
        if (found != null) {
            found.enroll();
            found.enroll();
        }

        removeCourse(courses, "C01");

        for (Course course : courses) {
            System.out.println(course);
        }
    }

    public static void addCourse(ArrayList<Course> courses, Course course) {
        if (findCourse(courses, course.getCode()) == null) {
            courses.add(course);
        }
    }

    public static Course findCourse(ArrayList<Course> courses, String code) {
        for (Course course : courses) {
            if (course.getCode().equalsIgnoreCase(code)) {
                return course;
            }
        }
        return null;
    }

    public static boolean removeCourse(ArrayList<Course> courses, String code) {
        Course found = findCourse(courses, code);
        if (found == null) {
            return false;
        }
        return courses.remove(found);
    }
}
```

執行結果：

```text
C02 | Data Structures | enrolled=2
```

#### 執行重點

建立檔案後先確認檔名與 `public class` 一致，再觀察每次操作前後的集合內容與 `size()`。不要只看最後輸出，要能指出是哪一行讓資料增加、取代、移動或被刪除。

---

### 概念 12：選擇陣列或 ArrayList

#### 概念說明

選擇資料結構前，要先問資料數量是否固定、是否需要頻繁新增刪除、是否依索引讀取，以及資料是基本型態還是物件。固定月份、棋盤或感測器通道適合陣列；動態名單與物件管理通常適合 `ArrayList`。

`ArrayList` 的方便來自自動管理容量，但中間插入與刪除仍需要搬移後方資料。資料結構沒有絕對最好，只有是否符合主要操作。

#### 實際應用

- 固定十二個月份使用陣列。
- 不固定報名人數使用 ArrayList。
- 大量前端刪除改考慮 Linked List 或 Queue。

#### 資料變化

需求改變時，選擇也可能改變；先列出主要操作，再決定結構。

#### 何時適合使用

把選擇理由寫入設計與註解，而不是看到集合就一律使用 ArrayList。

#### 範例程式

檔名：`CollectionChoiceDemo.java`

```java
import java.util.ArrayList;

public class CollectionChoiceDemo {
    public static void main(String[] args) {
        double[] monthlyRates = new double[12];
        monthlyRates[0] = 1.2;
        monthlyRates[1] = 1.5;

        ArrayList<String> registrations = new ArrayList<>();
        registrations.add("Amy");
        registrations.add("Ben");
        registrations.remove("Amy");

        System.out.println("固定月份數：" + monthlyRates.length);
        System.out.println("目前報名數：" + registrations.size());
    }
}
```

執行結果：

```text
固定月份數：12
目前報名數：1
```

#### 執行重點

建立檔案後先確認檔名與 `public class` 一致，再觀察每次操作前後的集合內容與 `size()`。不要只看最後輸出，要能指出是哪一行讓資料增加、取代、移動或被刪除。

---

## 課堂實作題

### 課堂實作題一：動態成績管理

建立以下檔案：

- `DynamicScoreManager.java`

功能要求：

1. 持續輸入成績直到輸入 -1。
2. 只接受 0 到 100。
3. 使用 ArrayList 保存。
4. 輸出筆數、平均、最高、最低與及格名單。
5. 統計與篩選必須拆成 method。

完成標準：檔名與 `public class` 一致、程式可直接編譯執行、輸入與錯誤情況都有處理。

---

### 課堂實作題二：名單管理

建立以下檔案：

- `NameListManager.java`

功能要求：

1. 提供新增、搜尋、修改、刪除與列出全部功能。
2. 姓名比對忽略英文大小寫。
3. 不得加入空白姓名。
4. 刪除後必須顯示成功或找不到。

完成標準：檔名與 `public class` 一致、程式可直接編譯執行、輸入與錯誤情況都有處理。

---

### 課堂實作題三：設備物件集合

建立以下檔案：

- `Equipment.java`
- `EquipmentManager.java`

功能要求：

1. Equipment 至少包含代碼、名稱與可借用狀態。
2. 以 ArrayList<Equipment> 保存。
3. 支援新增、依代碼搜尋、借出、歸還與列出可借設備。
4. 代碼不可重複。

完成標準：檔名與 `public class` 一致、程式可直接編譯執行、輸入與錯誤情況都有處理。


## 課後作業

以下三題全部放在 `0721` 資料夾，並依 iClass 當天作業區要求繳交 `0721` 資料夾的 GitHub 連結。

### 課後作業一：聯絡人管理系統

建立以下檔案：

- `Contact.java`
- `ContactBookSystem.java`

功能要求：

1. 聯絡人包含代碼、姓名、電話與電子郵件。
2. 支援新增、搜尋、修改電話、刪除與完整清單。
3. 代碼不可重複，空白姓名不可加入。
4. 至少使用 5 個自訂 method。

完成標準：檔名與 `public class` 一致、程式可直接編譯執行、輸入與錯誤情況都有處理。

---

### 課後作業二：購物車系統

建立以下檔案：

- `CartItem.java`
- `ShoppingCartSystem.java`

功能要求：

1. 商品包含代碼、名稱、單價與數量。
2. 重複加入相同代碼時增加數量，不建立重複物件。
3. 支援修改數量、移除商品與計算總額。
4. 數量小於或等於 0 時不接受更新。

完成標準：檔名與 `public class` 一致、程式可直接編譯執行、輸入與錯誤情況都有處理。

---

### 課後作業三：選課管理系統

建立以下檔案：

- `Course.java`
- `CourseRegistrationSystem.java`

功能要求：

1. 課程包含代碼、名稱、容量與目前人數。
2. 支援新增課程、選課、退選、刪除與搜尋。
3. 課程額滿後不可再增加人數。
4. 輸出總課程數、總選課人次與額滿課程。

完成標準：檔名與 `public class` 一致、程式可直接編譯執行、輸入與錯誤情況都有處理。


## 常見錯誤與診斷

- `IndexOutOfBoundsException`：檢查合法索引是否為 0 到 `size() - 1`。
- 把 `set()` 當成新增：空集合必須先使用 `add()`。
- `ArrayList<Integer>.remove(1)` 刪錯資料：確認要刪索引還是整數內容。
- 正向迴圈刪除後漏掉資料：改用倒序索引。
- 物件搜尋只比較名稱：改用唯一代碼。
- method 意外修改原集合：需要保留原資料時建立新集合。

## 評分規準

- 資料結構與泛型選擇正確。
- CRUD 功能完整且處理找不到或重複資料。
- method 拆分清楚，沒有把所有程式放在 `main()`。
- 檔名、類別名稱與輸出標籤正確。
- 正常、空集合、邊界與錯誤資料都能處理。
