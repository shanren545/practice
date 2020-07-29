package shanren.interview;

public class KuaiShou {
    public static void main(String[] args) {
        LinkedNode head = null ;
        LinkedNode tail = null;
        
        for(int i = 0 ; i<10 ; i++){
           LinkedNode  tmp =  new LinkedNode();
            tmp.val = i;
            if(i == 0){
                head = tmp;
                tail = tmp;
            } 
            
            tail.next = tmp;
            tail = tail.next;
        }
        
        
        LinkedNode reveresLink = reveresLink(head);
        System.out.println (reveresLink.val);
        while(reveresLink != null) {
            System.out.println (reveresLink.val);
            reveresLink = reveresLink.next;
        }
        
        //Scanner in = new Scanner(System.in);
        //int a = in.nextInt();
        //System.out.println(a);
        System.out.println("Hello World!");
    }
    
    public static LinkedNode reveresLink(LinkedNode head){
        if(null == head){
            return null;
        }
        
        LinkedNode pre = null;
        LinkedNode curr = head;
       // head = head.next;
        //newHead.next = null;
        
        LinkedNode tmp ;
        
        while(curr != null){
           tmp = curr;
           curr = curr.next;
           
           tmp.next = pre;
           pre = tmp;
        }

       return pre;
    }
 
     public static class LinkedNode {
        public LinkedNode next;
        public int val;
    }
   
}

