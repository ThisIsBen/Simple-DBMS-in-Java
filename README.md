# Simple-DBMS-in-Java
DBMS Term Project Document
H34026204_陳昭成 



使用環境
我使用java 完成DBMS Term Project，並使用Eclipse IDE 進行開發及程式執行。

Input :
Input file: book.txt  , sellRecord.txt
Content of book.txt:
/* isbn|author|title|price|subject */
439139597|J. K. Rowling|Harry Potter and the goblet of fire|25.95|CHILDREN
439064864|J. K. Rowling|Harry Potter and the chambers of secrets|17.95|CHILDREN
439136350|J. K. Rowling|Harry Potter and the chamber of secrets|19.95|CHILDREN
345350499|Marion Zimmer Bradley|The mists of avalon|12.95|FICTION
395851580|James Marshall|George and Martha the complete stories of two best friends|25|CHILDREN
375810609|Jean DE Brunhoff|Bonjour Babar|29.95|CHILDREN
345337662|Anne Rice|Interview with a vampire|6.99|FICTION
345377648|Anne Rice|Lasher|14|FICTION
345313860|Anne Rice|The vampire lestat|6.99|FICTION
042510107X|Tom Clancy|Red Storm Rising|7.99|FICTION
034538475X|Anne Rice|The tale of the body thief|6.99|FICTION

Content of sellRecord.txt:
/* uid|no|isbn_no */
1|1|439139597
2|1|439136350
3|1|439064864
15|10|042510107X
4|2|439139597
5|2|439136350
33|23|33
44|55|555

Input of command line in Eclipse:
You can type in the SQL query below:
SELECT * FROM books; 
SELECT title,author FROM books;
SELECT title,author FROM books WHERE author =‘J. K. Rowling’;
SELECT title FROM books,sellRecord WHERE isbn = isbn_no AND author =‘J. K. Rowling’; 
SELECT DISTINCT author FROM books;



Output:
SQL query result 




使用說明
執行後的結果: 可讓使用者直接輸入SQL query。
 

輸入SQL query 做查詢。
 

得到SQL query 查詢結果。
 

系統架構
本系統共有五個class，分別為:DuplicatMap、ReadInData、TargetAttr 、Node、DBMSMain。

ReadInData用以讀取books.txt 與 sellRecord.txt中的所有資料，並分別儲存在所建立的booksTable與sellRecordTable中。

DuplicatMap用以實作當attribute hash table Collision發生時，將所有map到同一bucket的列數串接起來的Chaining 作法。 

TargetAttr用以記錄SQL query欲取得的attibute是屬於哪個Table以及該attribute在所屬的Table中的哪一行。

Node用以處理Collision，藉由標示每個查詢值所在的列數外，也標示該列數是在說哪個值。以處理在Collision發生時，從attribute hash table 取出目標bucket中所有的列數時，透過標示該列數是在說哪個值可以知道哪些列數是使用者所要搜尋的結果。

DBMSMain為main method所在的class，用於為booksTable 與 sellRecordTable中的所有attribute建立各自的hash table 及可馬上辨識所要SELECT的attribute是在booksTable 或是 sellRecordTable中的第幾行的 hash table booksATH,sellRecordATH ，並讀進使用者輸入的SQL query，進行整個搜尋的實作。
實作方法說明
所建立的表格:
booksTable:記錄所有books.txt中的所有內容
sellRecordTable: 記錄所有books.txt中的所有內容


booksATH:為一個可將booksTable中的attribute對應到該attribute所在的Column 的hash table。
例如:authorbooksATH1 , 如此即可得知所有屬於author這個屬性的值都存在booksTable中的第1行。
sellRecordATH:為一個可將sellRecordTable中的attribute對應到該attribute所在的Column 的hash table。
例如:uidsellRecordATH0 , 如此即可得知所有屬於uid這個屬性的值都存在sellRecordTable中的第0行。

每個attribute都建立各自將其值對應到該值是存在所屬Table(booksTable 或 sellRecordTable)中的第幾列的hash table。
如: 為booksTable中的attribute author 建立 authorHT這個hash table，
‘Anne Rice’authorHT6,7,8,10  ,  
表示Anne Rice這個屬於author屬性的值出現在booksTable中的第6,7,8,10列。

因此，以下為所有為booksTable及sellRecordTable中的每個attribute所建立的將其值對應到該值是存在所屬Table(booksTable 或 sellRecordTable)中的第幾列的hash table:
為在booksTable中的attribute isbn 建立 isbnHT
為在booksTable中的attribute author建立 authorHT
為在booksTable中的attribute title 建立 titleHT
為在booksTable中的attribute price 建立 priceHT
為在booksTable中的attribute subject 建立 subjectHT
為在sellRecordTable中的attribute uid 建立 uidHT
為在sellRecordTable中的attribute no 建立 noHT
為在sellRecordTable中的attribute isbn_no 建立 isbn_noHT

運作方式:
分為Query中沒有WHERE及Query中有WHERE的情況，
在沒有WHERE的情況:
Query: SELECT * FROM books; 
若在SELECT後讀到*,就印出FROM後方寫的表格中的所有內容，在此例中為直接印出booksTable中的所有內容。

Query:SELECT title,author FROM books;
若在SELECT後讀到attribute name，就在讀FROM後方寫的表格名稱，以得知是要取得哪個表格中的attribute值。
此例為取得booksTable中的attribute title,author，這時就將titlebooksATH(對應出title直接存在booksTable中的第幾行)2(得出title直接放在booksTable中的第2行)。
而author也是authorbooksATH(對應出author直接存在booksTable中的第幾行)1(得出author直接放在booksTable中的第1行)。
得知title及author值存分別存在booksTable中的第1及第2行後，就印出booksTable中第1及第2行的所有內容即可得到所求。

Query: SELECT title,author FROM books WHERE author =‘J. K. Rowling’;
這時就將titlebooksATH(對應出title直接存在booksTable中的第幾行)2
(得出title直接放在booksTable中的第2行)。
而author也是authorbooksATH(對應出author直接存在booksTable中的第幾行)1(得出author直接放在booksTable中的第1行)。

接著，判斷條件author =‘J. K. Rowling’中的J. K. Rowling是否為booksTable或sellRecord中的attribute，結果因為J. K. Rowling 不屬於booksTable或sellRecord中的attribute而是個值，因此，直接將J. K. Rowling輸入hash function去查詢 hash table authorHT，找出J. K. Rowling在booksTable中所在的列數0,1,2。

最後取得booksTable中的booksTable[2][0], booksTable[2][1], booksTable[2][2]
即為欲取得的title值。
取得booksTable中的booksTable[1][0], booksTable[1][1], booksTable[1][2]
即為欲取得的author值。

Query: SELECT title FROM books,sellRecord 
WHERE isbn = isbn_no AND author =‘J. K.Rowling’; 
這時就將titlebooksATH(對應出title直接存在booksTable中的第幾行)2
(得出title直接放在booksTable中的第2行)。

先看第一個條件: isbn = isbn_no，判斷isbn_no是否為booksTable或sellRecord中的attribute，結果發現isbn_no是sellRecord中的attribute，因此便將isbn_no的值全部輸入負責處理isbn值與列數對應的hash table isbnHT，若有sellReocordTable 中的’isbn_no值也存在booksTable中的isbn值中，就將該列數記錄下來，而得到0,2,9,0,2。
接著，看第二個條件: author =‘J. K.Rowling’
判斷條件author =‘J. K. Rowling’中的J. K. Rowling是否為booksTable或sellRecord中的attribute，結果因為J. K. Rowling 不屬於booksTable或sellRecord中的attribute而是個值，因此，直接將J. K. Rowling輸入hash function去查詢 hash table authorHT，找出J. K. Rowling在booksTable中所在的列數0,1,2。

最後，再將第一個條件: isbn = isbn_no找到的列數:0,2,9,0,2 與 第二個條件: author =‘J. K.Rowling’ 找到的列數0,1,2 取重複項作保留，因此只有0,2被保留下來。
因為欲取得的attribute title放在booksTable中的第2行，因此，取booksTable[2][0], booksTable[2][2]即完成所求。

Query: SELECT DISTINCT author FROM books;
當query中含有DISTINCT時，就將取得的author值一一記錄在一個ArrayList中，若發現當下要加入ArrayList的author已有存在ArrayList了，就不須將該重複值輸出螢幕。

Collision handling:
因為在使用助教提供的hash function時，會出現多個值被分到hash table中的同一個bucket中,例如: attribute author 中的James Marshall與Tom Clancy輸入到hash function後都分配到同一個bucket中。

因此欲從負責存author attribute name對應到該attribute name在Table中所存位置的列數的hash table authorHT中取得James Marshall 所在的列數時，也會依同取得Tom Clancy所在的列數，若無特別標示，是無法得知得到的數個列數中那些是指James Marshall所在的列數。

為了解決此問題，我使用創立名叫Node的class,內容如下:
Class Node
{
	String attributeName; //存輸入hash function的attribute name.
	Int position;//存該輸入的attribute name 在 Table 中的列數
	Setter/Getter for attributeName;
	Setter/Getter for position;
}
如此一來，只要在建立hash table authorHT 時，除了標示每個值所在的列數外，也標示該列數是在說哪個值。
之後在使用hash table authorHT 去找James Marshall所在的列數時，即使會有其他同樣在該bucket中的其他author的列數被輸出，只要去查看每個輸出的列數的attribute name是James Marshall的，就將該列數保留，而最後被保留下來的將都會是James Marshall所在的列數。



