package com.sora.utils;

import com.sora.domain.Comment;
import com.sora.vo.CommentVO;

import java.util.*;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/10 16:12
 */
public class CommentsSortUtils {

    private static int[] father;
    private static int[] height;
    private static Map<Long,Integer> kvMap;

    public static void init(List<Comment> commentList){
        father = new int[commentList.size()];
        height = new int[commentList.size()];
        kvMap = new HashMap<Long,Integer>();
        for(int i=0;i<commentList.size();i++){
            father[i] = i;
            kvMap.put(commentList.get(i).getId(),i);
            height[i] = 1;
        }
    }


    public static int findFather(int i){
        if(father[i]==i){
            return father[i];
        }else{
            return findFather(father[i]);
        }
    }

    public static void union(int x,int y){
        x = findFather(x);
        y = findFather(y);
        if(x!=y){
            father[x] = y;
        }
    }

    public static List<CommentVO> sort(List<Comment> commentList){
        List<CommentVO> result = new ArrayList<CommentVO>();
        Map<Integer,CommentVO> resultMap = new HashMap<Integer, CommentVO>();
        commentList.sort(new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                return (int)(o1.getId()-o2.getId());
            }
        });
        init(commentList);
        for(int i=0;i<commentList.size();i++){
            if(commentList.get(i).getPreId()!=0){
                int x = kvMap.get(commentList.get(i).getId());
                int y = kvMap.get(commentList.get(i).getPreId());
                union(x,y);
            }
        }
        for(int i=0;i<father.length;i++){
            father[i] = findFather(i);
        }
        for(int i=0;i<father.length;i++){
            if(father[i]==i){
                CommentVO commentVO = new CommentVO();
                commentVO.setPreComment(commentList.get(i));
                commentVO.setPostCommentList(new ArrayList<Comment>());
                resultMap.put(i,commentVO);
            }else{
                CommentVO commentVO = resultMap.get(father[i]);
                commentVO.getPostCommentList().add(commentList.get(i));
                resultMap.put(father[i],commentVO);
            }
        }
        Iterator it = resultMap.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            CommentVO commentVO = (CommentVO) entry.getValue();
            commentVO.getPostCommentList().sort(new Comparator<Comment>() {
                @Override
                public int compare(Comment o1, Comment o2) {
                    if(o1.isTop()){
                        return -1;
                    }else if(o2.isTop()){
                        return 1;
                    }else{
                        return (int)(o2.getId()-o1.getId());
                    }
                }
            });
            result.add(commentVO);
        }
        result.sort(new Comparator<CommentVO>() {
            @Override
            public int compare(CommentVO o1, CommentVO o2) {
                if(o1.getPreComment().isTop()){
                    return -1;
                }else if(o2.getPreComment().isTop()){
                    return 1;
                }else{
                    return (int)(o2.getPreComment().getId()-o1.getPreComment().getId());
                }
            }
        });
        return result;
    }
}
