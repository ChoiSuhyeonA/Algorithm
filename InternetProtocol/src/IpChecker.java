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

  //block ip���� whiteip�� ���ºκ�
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

  //���������� spf�� �����ϴºκ�
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
  * ip�ּҸ� 10������ ��ȯ�Ѵ�.
  * */
 public HashMap ipConvert(String ip){
  String arrIp[] = ip.split("\\.");
  int band = 0;
  HashMap retVal = new HashMap();

  if(arrIp.length == 4){
   //ip������ �´°��
   String arrBand[] = arrIp[3].split("/");
   long startLong = 0l;
   long endLong = 0l;
   if(arrBand.length == 2){
    //������ �ִ°��
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
    //������ ���°��
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
   //ip������ Ʋ�����
   return null;
  }
  return retVal;
 }

 /**
  * ������ �������� ������ ArrayList�� ��ȯ�Ѵ�.</br>
  * ����� :</br>
  * ������ 2������ �����Ҷ� ���° �ڸ����� 0�� �������� Ȯ���Ѵ�(band) </br>
  * �ڸ����� Ȯ�εǸ� (2^band -1)�� �Ͽ� ���۰����� �����Ͽ� 1���� ������ �����ȴ�</br>
  * ������ �����Ǹ� ������ 2^band�� �����Ͽ� ���� �ܰ踦 ��ģ��</br>
  * ������ ������������ ���۰��� �������� Ŭ ��� ������ �ȴ�.</br>
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
    //Ȧ��
    if((calNum & 1) == 1){
     band++;
    }else{
     break;
    }
   }
   calNum = end;
   //
   long startNum = calNum - (long)(Math.pow(2,band) -1);
   //���۰����� �۴ٸ� ���۰��� �Ķ���ͷ� ������ �������� Ż���Ѵ�.
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
  * 10������ ��ȯ�� ���ڸ� ip�� ��ȯ�Ѵ�.
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

  //��ø�Ǵºκ��� ���°��
  if(blockStart > whiteEnd || blockEnd < whiteStart){
   HashMap ipInfo = new HashMap();
   ipInfo.put("start", blockStart);
   ipInfo.put("end", blockEnd);
   retVal.add(ipInfo);
   return retVal;
  }

  if(blockStart == whiteStart && blockEnd == whiteEnd){
   //ȭ��Ʈ �����ǰ� ������ �б⶧���� ��� block���� �����ȴ�
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
  * ip������ �����Ѵ�.
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
     //�񱳴���� �����󺸴� ������ ū���
     retVal.remove(j);
     retVal.add(list.get(i));
    }else if(start < tempStart && end < tempEnd) {
     //�񱳴���� �����󺸴� ������ �������
    }else if(tempStart > end || tempEnd < start){
     //���������� ������
     retVal.add(list.get(i));
    }else if(tempStart < start && tempStart >= end && tempEnd >= end){
     //���������� ��ġ�°��
     HashMap ip = new HashMap();
     ip.put("start", Long.valueOf(start));
     ip.put("end", Long.valueOf(tempEnd));
     retVal.add(ip);
    }else if(start < tempStart && tempEnd >= start && tempEnd < end){
     //���������� ��ġ�°��
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


