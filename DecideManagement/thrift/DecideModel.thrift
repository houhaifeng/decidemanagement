namespace java com.decide.model

struct ListParam {
	1: i32 offset;
	2: i32 length;
	3: i32 sort;
}
exception CatchableException {
	1: required i64 error;
	2: required string description;
	3: optional string causeInfo;
}

struct Product {
    1: i64 id;
    2: string name;
    3: string link;
    4: string category;
    5: string brand;
    6: string image;
    7: i32 viewRate;
    8: i32 collectRate;
    9: bool visibilty;
    10: double score;
    11: string parameters;
    12: i64 createDate;
    13: i64 sourceId;
	14: string source;
	15: bool exist;
	16: double price;
}

struct SourceProduct {
    1: i64 id;
    2: i64 productId;
    3: string source;
    4: i64 sourceId;
    5: bool exist;
}

struct Category  {
    1: i32 id;
    2: string name;
    3: string idenityname;
    4: string description;
    5: i32 pid;
    6: i32 grade;
    7: string uri;
    8: string orders;
}

struct Price {
    1: i64 id;
    2: i64 productId;
    3: double price;
    4: bool promotion;
    5: string festival;
    6: i32 weather;
    7: string city;
    8: i32 newModel;
    9: i64 createDate;
}

struct Comment  {
    1: i32 id;
    2: string title;
    3: string content;
    4: i32 starnum;
    5: i64 createDate;
    6: i32 state;
    7: i32 pid;
    8: i32 uid;
    9: string orderid;
}

struct User {
    1: i32 id;
    2: string mail;
    3: string password;
    4: i64 date;
    5: string role;
}
