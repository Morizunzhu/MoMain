package mo.hyit.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mo.hyit.entity.ChatterUIMessage;

public class XmlTool {
    private Context activity;
    private Document document;
    private SAXReader saxReader;
    private DateFormat dateFormat;
    private static String path = "list.xml";

    public XmlTool(Context activity) {
        this.activity = activity;
        saxReader = new SAXReader();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 读取聊天文件
     *
     * @param userId
     * @param friendId
     * @return
     */
    public List<ChatterUIMessage> readChatInfo(String userId, String friendId) {
        List<ChatterUIMessage> list = null;
        try {
            FileInputStream fis = getChatFileis(userId);
            document = saxReader.read(fis);

            Element root = document.getRootElement();
            //遍历所有<list>
            for (Iterator root_it = root.elementIterator("list"); root_it.hasNext(); ) {
                Element onelist = (Element) root_it.next();
                if (onelist.attributeValue("friendId").equals(friendId)) {
                    Element item;
                    for (Iterator it = onelist.elementIterator(); it.hasNext(); ) {
                        item = (Element) it.next();
                        if ("me".equals(item.getName())) {
                            ChatterUIMessage msg = new ChatterUIMessage(item.getText(), ChatterUIMessage.TYPE.SENT);
                            if (list == null) {
                                list = new ArrayList<>();
                            }
                            list.add(msg);
                        } else if ("you".equals(item.getName())) {
                            ChatterUIMessage msg = new ChatterUIMessage(item.getText(), ChatterUIMessage.TYPE.RECEIVED);
                            if (list == null) {
                                list = new ArrayList<>();
                            }
                            list.add(msg);
                        }
                    }
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("文件未找到错误！", e.getMessage());
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Map<String, List<ChatterUIMessage>> readChatInfoList(String userId) {
        Map<String, List<ChatterUIMessage>> map = null;
        try {
            List<ChatterUIMessage> list = null;
            FileInputStream fis = getChatFileis(userId);
            document = saxReader.read(fis);

            Element root = document.getRootElement();
            //遍历所有<list>
            for (Iterator root_it = root.elementIterator("list"); root_it.hasNext(); ) {
                Element onelist = (Element) root_it.next();
                String friendId = onelist.valueOf("friendId");

                //和某个好友的聊天记录
                List<Element> chatinfos = onelist.elements();
                List<ChatterUIMessage> friend_chatinfo = null;
                Element item;
                for (Iterator it = onelist.elementIterator(); it.hasNext(); ) {
                    item = (Element) it.next();
                    if ("me".equals(item.getName())) {
                        ChatterUIMessage msg = new ChatterUIMessage(item.getText(), ChatterUIMessage.TYPE.SENT);
                        if (friend_chatinfo == null) {
                            friend_chatinfo = new ArrayList<>();
                        }
                        friend_chatinfo.add(msg);
                    } else if ("you".equals(item.getName())) {
                        ChatterUIMessage msg = new ChatterUIMessage(item.getText(), ChatterUIMessage.TYPE.RECEIVED);
                        if (friend_chatinfo == null) {
                            friend_chatinfo = new ArrayList<>();
                        }
                        friend_chatinfo.add(msg);
                    }
                }
                if (map == null) {
                    map.put(friendId, friend_chatinfo);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将聊天信息写入文件
     *
     * @param userId
     * @param friendId
     * @param content
     * @param type
     */
    public void writeChatInfoToFile(String userId, String friendId, String content, String type) {
        FileInputStream fis = null;
        try {
            fis = getChatFileis(userId);
            document = saxReader.read(fis);

            Element root = document.getRootElement();

            //如果存在list
            boolean flag = false;
            for (Iterator root_it = root.elementIterator("list"); root_it.hasNext(); ) {
                Element onelist = (Element) root_it.next();
                if (onelist.attributeValue("friendId").equals(friendId)) {
                    List<Element> elements = onelist.elements();
                    Element new_info = DocumentHelper.createElement(type);
                    new_info.addAttribute("time", nowTime());
                    new_info.setText(content);
                    onelist.add(new_info);
                    flag = true;
                    break;
                }
            }
            //如果不存在list
            if (flag == false) {
                Element list = root.addElement("list");
                list.addAttribute("friendId",friendId);
                Element new_info = DocumentHelper.createElement(type);
                new_info.addAttribute("time", nowTime());
                new_info.setText(content);
                list.add(new_info);
            }
            writeToFile(userId);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(String userId) throws IOException {
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
        XMLWriter writer = new XMLWriter(activity.openFileOutput(userId + path, Context.MODE_PRIVATE), format);
        writer.write(document);
        writer.close();
    }

    private FileInputStream getChatFileis(String userId) throws IOException {
        makeFileIfNotExist(userId);
        return activity.openFileInput(userId + path);

    }

    private String nowTime() {
        return dateFormat.format(System.currentTimeMillis());
    }

    private void makeFileIfNotExist(String userId) throws IOException {
        String tmp = activity.getApplicationContext().getFilesDir().getAbsolutePath() + "/" + userId + path;
        Log.i("判断文件是否存在", "判断文件路径为：" + tmp);
        File file = new File(tmp);
        if (!file.getParentFile().exists() && !file.isDirectory()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            document = DocumentHelper.createDocument();
            document.setXMLEncoding("utf-8");
            document.addElement("lists");
            writeToFile(userId);
            return;
        }
        if (!file.exists()) {
            file.createNewFile();
            document = DocumentHelper.createDocument();
            document.setXMLEncoding("utf-8");
            document.addElement("lists");
            writeToFile(userId);
            return;
        }
    }
}

/**
 * SimpleDateFormat函数语法：
 * <p>
 * G 年代标志符
 * y 年
 * M 月
 * d 日
 * h 时 在上午或下午 (1~12)
 * H 时 在一天中 (0~23)
 * m 分
 * s 秒
 * S 毫秒
 * E 星期
 * D 一年中的第几天
 * F 一月中第几个星期几
 * w 一年中第几个星期
 * W 一月中第几个星期
 * a 上午 / 下午 标记符
 * k 时 在一天中 (1~24)
 * K 时 在上午或下午 (0~11)
 * z 时区
 */
