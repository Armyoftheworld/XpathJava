package com.army.xpath;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;

public class XpathTest {

    public static void main(String[] args) throws Exception{
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File("html.xml"));
        getNodesByAxis(document);
    }

    /**
     * 获取所有节点
     * @param document
     * @throws Exception
     */
    private static void getAllNodes(Document document) throws Exception{
        List<Node> nodes = document.selectNodes("//*");
        for (Node node : nodes) {
            System.out.println(node);
        }
    }

    /**
     * 获取节点内容及属性值
     * @param document
     * @throws Exception
     */
    private static void getNodes(Document document) throws Exception{
        List<Node> nodes = document.selectNodes("//receiver/intent-filter/action");
        for (Node node : nodes) {
            Element element = (Element) node;
            System.out.println("text = " + element.getTextTrim());
            System.out.println("name = " + element.attributeValue("name"));
        }
    }

    /**
     * 获取指定节点的属性值
     * @param document
     * @throws Exception
     */
    private static void getNodeAttributes(Document document) throws Exception {
        List<Node> nodes = document.selectNodes("//receiver/intent-filter/action/@android:name");
        for (Node node : nodes) {
            Attribute attribute = (Attribute) node;
            System.out.println(attribute.getNamespace());
            System.out.println(attribute.getValue());
        }
    }

    /**
     * 获取指定节点的父节点的属性
     * @param document
     * @throws Exception
     */
    private static void getParentNodes(Document document) throws Exception {
        System.out.println("方法一：------------start");
        List<Node> nodes = document.selectNodes("//intent-filter/../@android:name");
        for (Node node : nodes) {
            Attribute attribute = (Attribute) node;
            System.out.println(attribute.getNamespace());
            System.out.println(attribute.getValue());
        }
        System.out.println("方法一：------------end");
        System.out.println("-----------------------------------------------");
        System.out.println("方法二：------------start");
        nodes = document.selectNodes("//intent-filter/parent::*/@android:name");
        for (Node node : nodes) {
            Attribute attribute = (Attribute) node;
            System.out.println(attribute.getNamespace());
            System.out.println(attribute.getValue());
        }
        System.out.println("方法二：------------end");
    }

    /**
     * 根据属性找节点
     * @param document
     * @throws Exception
     */
    private static void getNodesByAttr(Document document) throws Exception {
        List<Node> nodes = document.selectNodes("//activity[@android:screenOrientation=\"portrait\"]");
        for (Node node : nodes) {
            Element element = (Element) node;
            System.out.println(element.attributeValue("name"));
        }
    }

    /**
     * 获取节点的文本
     * @param document
     * @throws Exception
     */
    private static void getNodeText(Document document) throws Exception {
        List<Node> nodes = document.selectNodes("//intent-filter/text()");
        for (Node node : nodes) {
            Text text = (Text) node;
            System.out.println(text.getText());
        }
    }

    /**
     * 属性多值匹配和多属性匹配
     * @param document
     * @throws Exception
     */
    private static void getNodesByMoreAttr(Document document) throws Exception {
        List<Node> nodes = document.selectNodes("//activity[contains(@android:name, \".myself.\") " +
                "and @android:screenOrientation=\"portrait\"]");
        for (Node node : nodes) {
            Element element = (Element) node;
            System.out.println(element.attributeValue("name"));
        }
    }

    /**
     * 按照指定的顺序获取节点的信息
     * @param document
     * @throws Exception
     */
    private static void getNodesByOrder(Document document) throws Exception {
        // 获取第一个
        System.out.println("获取第一个");
        Attribute attribute = (Attribute) document.selectSingleNode("//activity[1]/@android:name");
        System.out.println(attribute.getValue());

        // 获取最后一个
        System.out.println("获取最后一个");
        attribute = (Attribute) document.selectSingleNode("//activity[last()]/@android:name");
        System.out.println(attribute.getValue());

        // 获取前两个
        System.out.println("获取前两个");
        List<Node> nodes = document.selectNodes("//activity[position() < 3]/@android:name");
        for (Node node : nodes) {
            attribute = (Attribute) node;
            System.out.println(attribute.getValue());
        }

        // 获取倒数第三个
        System.out.println("获取倒数第三个");
        attribute = (Attribute) document.selectSingleNode("//activity[last() - 2]/@android:name");
        System.out.println(attribute.getValue());
    }

    /**
     * 根据轴选择节点
     * @param document
     * @throws Exception
     */
    private static void getNodesByAxis(Document document) throws Exception {
        System.out.println("获取所有祖先节点");
        List<Node> nodes = document.selectNodes("//activity[1]/ancestor::*");
        for (Node node : nodes) {
            Element element = (Element) node;
            System.out.println(element.getName());
        }

        System.out.println("获取指定节点的所有application祖先节点");
        nodes = document.selectNodes("//activity[1]/ancestor::application");
        for (Node node : nodes) {
            Element element = (Element) node;
            System.out.println(element.getName());
        }

        System.out.println("获取当前节点所有属性值");
        nodes = document.selectNodes("//activity[1]/attribute::*");
        for (Node node : nodes) {
            Attribute attribute = (Attribute) node;
            System.out.println(attribute.getName() + " = " + attribute.getValue());
        }

        System.out.println("获取指定属性值的节点");
        nodes = document.selectNodes("//application/child::activity[contains(@android:name, \"myself\")]");
        for (Node node : nodes) {
            Element element = (Element) node;
            System.out.println(element.attributeValue("name"));
        }

        System.out.println("获取所有子孙节点中的指定节点");
        nodes = document.selectNodes("//application/descendant::action");
        for (Node node : nodes) {
            Element element = (Element) node;
            System.out.println(element.attributeValue("name"));
        }

        System.out.println("获取当前所有节点之后的第二个节点，当前其实就是第三个");
        nodes = document.selectNodes("//activity[1]/following::*[2]");
        for (Node node : nodes) {
            Element element = (Element) node;
            System.out.println(element.attributeValue("name"));
        }

        System.out.println("获取当前所有节点之后的所有同级节点");
        nodes = document.selectNodes("//receiver[1]/intent-filter/following-sibling::*");
        for (Node node : nodes) {
            System.out.println(node.selectSingleNode("./action").valueOf("@android:name"));
        }
    }
}
