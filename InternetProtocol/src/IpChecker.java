import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class IpChecker {

 /**
  * @param whiteIp
  * @param blockIp
  * @return
  */
 public List calculateBlockIp(String[] whiteIp, String[] blockIp){
  /* test */


  ArrayList retVal = new ArrayList();
  ArrayList result = new ArrayList();
  ArrayList white = new ArrayList();
  ArrayList block = new ArrayList();

  for(int whiteIdx=0; whiteIdx<whiteIp.length; whiteIdx++){
   white.add(ipConvert(whiteIp[whiteIdx]));
  }

  for(int blockIdx=0; blockIdx<blockIp.length; blockIdx++){
   block.add(ipConvert(blockIp[blockIdx]));
  }

  //block ip에서 whiteip를 빼는부분
  for(int whiteIdx=0; whiteIdx<white.size(); whiteIdx++){
   HashMap whiteIpInfo = (HashMap)white.get(whiteIdx);
   for(int blockIdx=0; blockIdx<block.size(); blockIdx++){
    HashMap blockIpInfo = (HashMap)block.get(blockIdx);
    List compareResult = compareIp((Long)whiteIpInfo.get("start"), (Long)whiteIpInfo.get("end"), (Long)blockIpInfo.get("start"), (Long)blockIpInfo.get("end"));

    if(compareResult != null){
     result.addAll(compareResult);
    }
   }
   block = new ArrayList();
   block.addAll(result);
   result = new ArrayList();
  }

  result = block;

  //최종적으로 spf로 변경하는부분
  for(int idx=0; idx<result.size(); idx++){
   HashMap ipInfo = (HashMap)result.get(idx);
   List whiteSpf = ipSlice((Long)ipInfo.get("start"), (Long)ipInfo.get("end"));
   retVal.addAll(whiteSpf);
  }

  for(int i=0; i<retVal.size(); i++){
   System.out.println("========ip:" + ((HashMap)retVal.get(i)).get("orgTxt"));
  }
  System.out.println("========total size:" + retVal.size());
  return retVal;
 }

 /*
  * ip주소를 10진수로 변환한다.
  * */
 public HashMap ipConvert(String ip){
  String arrIp[] = ip.split("\\.");
  int band = 0;
  HashMap retVal = new HashMap();

  if(arrIp.length == 4){
   //ip형식이 맞는경우
   String arrBand[] = arrIp[3].split("/");
   long startLong = 0l;
   long endLong = 0l;
   if(arrBand.length == 2){
    //구간이 있는경우
    arrIp[3] = arrBand[0];
    band = Integer.parseInt(arrBand[1]);

    startLong = (Long.parseLong(arrIp[0])<<24)
        + (Long.parseLong(arrIp[1])<<16)
        + (Long.parseLong(arrIp[2])<<8)
        + Long.parseLong(arrIp[3]);

    long tailNum = (long)Math.pow(2, 32-band) -1;
    endLong = startLong | tailNum;

    retVal.put("orgTxt", ip);
    retVal.put("start", Long.valueOf(startLong));
    retVal.put("end", Long.valueOf(endLong));
    retVal.put("band", Integer.valueOf(band));

   }else{
    //구간이 없는경우
    startLong = (Long.parseLong(arrIp[0])<<24)
      + (Long.parseLong(arrIp[1])<<16)
      + (Long.parseLong(arrIp[2])<<8)
      + Long.parseLong(arrIp[3]);

    retVal.put("orgTxt", ip);
    retVal.put("start", Long.valueOf(startLong));
    retVal.put("end", Long.valueOf(startLong));
    retVal.put("band", Integer.valueOf(band));
   }
   //System.out.println("ip:" + ip + " start:" + startLong + "  end:" + endLong);
  }else{
   //ip형식이 틀린경우
   return null;
  }
  return retVal;
 }

 /**
  * 최적의 구간으로 나누어 ArrayList로 반환한다.</br>
  * 계산방법 :</br>
  * 끝값을 2진수로 나열할때 몇번째 자리에서 0이 나오는지 확인한다(band) </br>
  * 자리수가 확인되면 (2^band -1)을 하여 시작값으로 지정하여 1개의 구간이 형성된다</br>
  * 구간이 설정되면 끝값을 2^band로 설정하여 같은 단계를 거친다</br>
  * 완전히 끝나는조건은 시작값이 끝값보다 클 경우 끝나게 된다.</br>
  * ArrayList
  * @param start
  * @param end
  * @return
  */
 public ArrayList ipSlice(long start, long end){
  ArrayList retVal = new ArrayList();
  HashMap ip = null;
  int band = 0;
  boolean chkBand = true;
  while(start <= end){
   long calNum = end;
   for(int idx=0; start != end && idx<32; idx++){
    calNum = end>>>idx;
    //홀수
    if((calNum & 1) == 1){
     band++;
    }else{
     break;
    }
   }
   calNum = end;
   //
   long startNum = calNum - (long)(Math.pow(2,band) -1);
   //시작값보다 작다면 시작값을 파라미터로 셋팅후 루프문을 탈출한다.
   ip = new HashMap();
   if(start > startNum){
    startNum = start;
   }

   ip.put("start", Long.valueOf(startNum));
   ip.put("end", Long.valueOf(calNum));
   if(band == 0){
    ip.put("orgTxt", naturalConverter(startNum));
    ip.put("band", Integer.valueOf(0));
   }else{
    ip.put("orgTxt", naturalConverter(startNum) + "/" + (32-band));
    ip.put("band", Integer.valueOf(32-band));
   }
   //System.out.println("orgTxt:"+ip.get("orgTxt"));
   retVal.add(ip);
   end = startNum - 1;
   band = 0;
  }
  return retVal;

 }

 /**
  * 10진수로 변환된 숫자를 ip로 변환한다.
  * @param num
  * @return
  */
 public String naturalConverter(long num){
  String retVal = "";
  //System.out.println(Long.toBinaryString(num));
  if(num != 0){
   //System.out.println((int)num<<8);
   int ip1 = (int)num>>>24;
   int ip2 = (int)num<<8>>>24;
   int ip3 = (int)num<<16>>>24;
   int ip4 = (int)num<<24>>>24;
   retVal = ip1 + "." + ip2 + "." + ip3 + "." + ip4;
  }
  //System.out.println(retVal);
  return retVal;
 }


 /**
  * @param whiteStart
  * @param whiteEnd
  * @param blockStart
  * @param blockEnd
  * @return
  */
 public List compareIp(long whiteStart, long whiteEnd, long blockStart, long blockEnd){
  List retVal = new ArrayList();

  //중첩되는부분이 없는경우
  if(blockStart > whiteEnd || blockEnd < whiteStart){
   HashMap ipInfo = new HashMap();
   ipInfo.put("start", blockStart);
   ipInfo.put("end", blockEnd);
   retVal.add(ipInfo);
   return retVal;
  }

  if(blockStart == whiteStart && blockEnd == whiteEnd){
   //화이트 아이피가 영역이 넓기때문에 모두 block에서 해제된다
   return null;
  }else {
   if(whiteStart > blockStart){
    if(whiteEnd >= blockEnd){
     HashMap ipInfo = new HashMap();
     ipInfo.put("start", blockStart);
     ipInfo.put("end", Long.valueOf(whiteStart - 1));
     retVal.add(ipInfo);

    }else{
     HashMap ipInfo = new HashMap();
     ipInfo.put("start", blockStart);
     ipInfo.put("end", Long.valueOf(whiteStart - 1));
     retVal.add(ipInfo);

     ipInfo = new HashMap();
     ipInfo.put("start", Long.valueOf(whiteEnd + 1));
     ipInfo.put("end", Long.valueOf(blockEnd));
     retVal.add(ipInfo);
    }
   }else{
    if(whiteEnd >= blockEnd){
     return null;
    }else{
     HashMap ipInfo = new HashMap();
     ipInfo.put("start", Long.valueOf(whiteEnd + 1));
     ipInfo.put("end", Long.valueOf(blockEnd));
     retVal.add(ipInfo);
    }
   }
  }

  for(int i=0; i<retVal.size(); i++){
   System.out.println("start:"+((HashMap)retVal.get(i)).get("start") + "  end:"+((HashMap)retVal.get(i)).get("end"));
  }
  return retVal;
 }

 /**
  * ip구간을 병합한다.
  * @param list
  * @return
  */
 public List sectionMerge(List list){
  List retVal = new ArrayList();
  for(int i=0; i<list.size(); i++){
   long start = ((Long)((HashMap)list.get(i)).get("start"));
   long end = ((Long)((HashMap)list.get(i)).get("end"));
   for(int j=0; j<retVal.size(); j++){
    long tempStart = ((Long)((HashMap)retVal.get(i)).get("start"));
    long tempEnd = ((Long)((HashMap)retVal.get(i)).get("end"));

    if(start >= tempStart && end >= tempEnd){
     //비교대상이 추출대상보다 구간이 큰경우
     retVal.remove(j);
     retVal.add(list.get(i));
    }else if(start < tempStart && end < tempEnd) {
     //비교대상이 추출대상보다 구간이 작은경우
    }else if(tempStart > end || tempEnd < start){
     //구간밖으로 벗어난경우
     retVal.add(list.get(i));
    }else if(tempStart < start && tempStart >= end && tempEnd >= end){
     //일정구간이 겹치는경우
     HashMap ip = new HashMap();
     ip.put("start", Long.valueOf(start));
     ip.put("end", Long.valueOf(tempEnd));
     retVal.add(ip);
    }else if(start < tempStart && tempEnd >= start && tempEnd < end){
     //일정구간이 겹치는경우
     HashMap ip = new HashMap();
     ip.put("start", Long.valueOf(tempStart));
     ip.put("end", Long.valueOf(end));
     retVal.add(ip);
    }
   }
  }
  return retVal;
 }
}


