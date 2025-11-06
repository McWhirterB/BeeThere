// package com.beethere.utils.sanitizer;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;

// public class sanitizeTest {
    
//     private sanitize sanitize;
    
//     @BeforeEach
//     public void setUp() {
//         sanitize = new sanitize();
//     }
    
//     @Test
//     public void testBasicHtmlIsPreserved() {
//         String input = "<script><b>You have been hacked</b></script>";
//         String result = sanitize.sanitizeHtml(input);
//         assertEquals("You have been hacked", result);
//     }
    
// }