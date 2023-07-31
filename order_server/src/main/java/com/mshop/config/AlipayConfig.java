package com.mshop.config;

public class AlipayConfig {
    // 商户appid
    public static String APPID = "9021000123615597";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCcphEzAE4URhi2cje9eXkHs+pFRXyUGhirdpurK0p3cb96WIk4Nz8MnyyIxsL6uBeoKFys5tsoNlJC7shmQvbak2O4rf8T/16gjMz34W2tmPe/crZNN+uDhfR2WSR4dRT4S8tkXl1woVWrDgZCmEuTljdWVHGxuYz7SImOJpMCgSj32pJrI84DF38CqNmTkrq9DAuHPB6yrom3ksKkt9d2vvdyvRj0vsSWE71GSoqGqM1xSe0AtVlPSeXlwG3fXyAkXU4lCakVl91LCNBPf7HVwEffoCOZqN0BtcTFt8I88qp+LxWaNFwzsr0KOTqNShP+cVOmmNZtRSekm62A1Q15AgMBAAECggEAVPS/yTA2KEqn5nLlhg4WNLY/rNf1q4DHtl46KMfut/7pwNm5edKEkDzXc0qQvlcPBXT2ZhNB8nobfQ9xgfx5Uquy3psHBvptgJc8JoeM6yRV4O++rPxR71+76F+KJwYgnFTG9yUyef4VrxKJFJ7yBhXm5xwfGSsvqax6ZazH+Afi0yY6LR7vZR2ImCVuFGhj9C05aI1gaCfJ/yLqCf3vws25TQf5Q3wNqgzx2GxhKJq9JFpj5R7PhyYK9bFi3SLDXXwMTiWH3sNm7iBxA/yeHVi2P9bOMPuauqowLdrYu1D+bleUNWasN84cbz2SDTEdMAVU37VAhPQlITTE7Rw48QKBgQD/jS1ka73RW1mgPwt+QEqqF+ALgIOB7YEYayquRewMoww8Qk7CztYsPszB6lvETCahpCvECCLtzKI26zjGUWjO7naU5FZoGzva7pr27DFuuouabiZqpFgJc4Pkz0EOUSYITD5plJZVYBneOk/RhuRadJsgIvT5gOthbuAlho5BVQKBgQCc7HOXzsXxVR1Xi76PD/aQzZzlUEoao1arOMtv+RFIwhHvBTxBTs1lr0fgMD9nEZPBG/tZLl6vxsA+T6d+MZtNYlwN+Y/Wuytmxm0e5YXsM11GIhph2FOzyHigZmXyCrby0zva9+AtdSIoBCqKbp3x8MlAds+7ZrA0RPlOcdDrlQKBgQCroTZ+Pjoi1FZSYGUoqqc6q4RGVU59QXS1YKwulTlel1bNVb8t6NpApwC/r9Vn9bjwUKEwLELBMPE1Ly9XSi+44sDzaJN7oebGOAANnD3q3xCJbDcS4kNaPaG0V1ma8vc3FXXJVQjOWBenf8RBtXXCJpRUtgWPWH09V8jkiwUZbQKBgD4eX9fNU5AoCFh6ijGpuQozSpOACAWpeQqIjgfCSbRKlrzOjr6viiFhlItLrh2H2LyN7h6se3ELtVg1QfFzg63LbPNfQjBXoWOq0tFzPGN3Jco4f8cR0niNuCqOvnZlBWz0b3JLw2NbRe0fURPBnCaFEbXCEpN1n/ThBOunpHNNAoGBAOocZangs0uKXZKvWCuw6Y3ZcjZqW/7tY97XRRuRPvjoEQ20JvnGk2NdL05xuH3WaXaFw4VXnhw2zJXqr5kFmNDsvrMyI/tswDRCNdmy/PEwNoDthO+EmIUJiYzljgESDFabr2Vcnqcjhbr/0omZmEWmEc47H6s9t0raw4qAc7ES";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8083/notify_url";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "http://localhost:8083/return_url";
    // 请求网关地址
    // public static String URL = "https://openapi.alipay.com/gateway.do";
    public static String URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmM6qHv80CJpQJSkwrbe6KrN8dU2vUigUMHOXXMmVNJGlTL3NuVbj+2r8USW0FLbOr+ZJrulJB38jjOg04T18sqs+iVUqXCX1lZptq6vOz/QOsnxw0wf3y4Xq+y3iLWb+mlkThzsoVqsKuZsZbSn9hHn8kMhcuAKyKSciSARKXHNjchctNAABPXzuIiJoi3Xo4SyH5agofMpRZvR4WNN7MpvYF+e4/5RGGhujuiQP9cDN4Va5c6Uqz4LZap5nJ+mmAaRJNKe/zG2WzZH2JYcMRx4V8+/YmtsH4h63Fla4S+zTdoE/BgJnIwEd6Z/3/fm7RPZHhVNjg0jKinVOtg9tAQIDAQAB";
    // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";
}
