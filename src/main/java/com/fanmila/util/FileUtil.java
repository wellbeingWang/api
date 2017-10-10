package com.fanmila.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import com.fanmila.util.http.HttpTookit;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 文件操作工具类.
 * 
 * @author David.Huang
 */
@SuppressWarnings("deprecation")
public class FileUtil {

	private static Logger log = LoggerFactory.getLogger(FileUtil.class);

	/** 默认编码方式 -UTF8 */
	private static final String DEFAULT_ENCODE = "utf-8";


	/**
	 * 构造方法
	 */
	public FileUtil() {
		// empty constructor for some tools that need an instance object of the
		// class
	}



	/**
	 * 下载文件保存到本地
	 * 
	 * @param path
	 *            文件保存位置
	 * @param url
	 *            文件地址
	 * @return 
	 * @throws IOException
	 */
	@SuppressWarnings({ "resource" })
	public static boolean downloadFile(String path, String url) throws IOException {
		log.debug("path:" + path);
		log.debug("url:" + url);
		HttpClient client = null;
		boolean success = true;
		try {
			// 创建HttpClient对象
			client = new DefaultHttpClient();
			// 获得HttpGet对象
			HttpGet httpGet = HttpTookit.getHttpGet(url, null, null);
			// 发送请求获得返回结果
			HttpResponse response = client.execute(httpGet);
			// 如果成功
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				byte[] result = EntityUtils.toByteArray(response.getEntity());
				BufferedOutputStream bw = null;
				try {
					// 创建文件对象
					File f = new File(path);
					// 创建文件路径
					if (!f.getParentFile().exists())
						f.getParentFile().mkdirs();
					// 写入文件
					bw = new BufferedOutputStream(new FileOutputStream(path));
					bw.write(result);
					log.info("保存文件成功,path=" + path + ",url=" + url);
				} catch (Exception e) {
					log.error("保存文件错误,path=" + path + ",url=" + url, e);
					success = false;
				} finally {
					try {
						if (bw != null)
							bw.close();
					} catch (Exception e) {
						log.error("finally BufferedOutputStream shutdown close",e);
						success = false;
					}
				}
			}
			// 如果失败
			else {
				StringBuffer errorMsg = new StringBuffer();
				errorMsg.append("httpStatus:");
				errorMsg.append(response.getStatusLine().getStatusCode());
				errorMsg.append(response.getStatusLine().getReasonPhrase());
				errorMsg.append(", Header: ");
				Header[] headers = response.getAllHeaders();
				for (Header header : headers) {
					errorMsg.append(header.getName());
					errorMsg.append(":");
					errorMsg.append(header.getValue());
				}
				log.error("HttpResonse Error:" + errorMsg);
				success = false;
			}
		} catch (ClientProtocolException e) {
			log.error("下载文件保存到本地,http连接异常,path=" + path + ",url=" + url, e);
			success = false;
			throw e;
		} catch (IOException e) {
			log.error("下载文件保存到本地,文件操作异常,path=" + path + ",url=" + url, e);
			success = false;
			throw e;
		} finally {
			try {
				client.getConnectionManager().shutdown();
			} catch (Exception e) {
				log.error("finally HttpClient shutdown error", e);
				success = false;
			}
		}
		return success;
	}
	
	/**
	 * 解压GZ文件，inFileName为源文件，outFilePath为解压后文件路径
	 * @param inFileName
	 * @param outFilePath
	 * @return
	 */
	public static boolean doUncompressFileofGZ(String inFileName, String outFilePath) {   
		boolean success = true;
        try {   
  
            if (!getExtension(inFileName).equalsIgnoreCase("gz")) {   
                System.err.println("File name must have extension of \".gz\"");  
    			success = false; 
                //System.exit(1);   
            }   
  
            System.out.println("Opening the compressed file.");   
            GZIPInputStream in = null;   
            try {   
                in = new GZIPInputStream(new FileInputStream(inFileName));   
            } catch(FileNotFoundException e) {   
                System.err.println("File not found. " + inFileName);   
    			success = false;
                //System.exit(1);   
            }   
  
            System.out.println("Open the output file.");   
            String outFileName = getFileName(inFileName); 
            outFileName = outFilePath+File.separator+outFileName;
            System.out.println(outFileName);
            FileOutputStream out = null;   
           try {   
                out = new FileOutputStream(outFileName);   
            } catch (FileNotFoundException e) {   
                System.err.println("Could not write to file. " + outFileName);  
    			success = false; 
                //System.exit(1);   
            }   
  
            System.out.println("Transfering bytes from compressed file to the output file.");   
            byte[] buf = new byte[1024];   
            int len;   
            while((len = in.read(buf)) > 0) {   
                out.write(buf, 0, len);   
            }   
  
            System.out.println("Closing the file and stream");   
            in.close();   
            out.close();   
          
        } catch (IOException e) {   
            e.printStackTrace();   
			success = false;
            //System.exit(1);   
        }   
      return success;
    }   
  

	public static boolean doUncompressFileofGZ(String inFileName) {   
		return doUncompressFileofGZ(inFileName, ".");
	}
    /**  
     * Used to extract and return the extension of a given file.  
     * @param f Incoming file to get the extension of  
     * @return <code>String</code> representing the extension of the incoming  
     *         file.  
     */   
    public static String getExtension(String f) {   
        String ext = "";   
        int i = f.lastIndexOf('.');   
  
        if (i > 0 &&  i < f.length() - 1) {   
            ext = f.substring(i+1);   
        }        
        return ext;   
    }   
  
    /**  
     * Used to extract the filename without its extension.  
     * @param f Incoming file to get the filename  
     * @return <code>String</code> representing the filename without its  
     *         extension.  
     */   
    public static String getFileName(String f) {   
        String fname = "";
        int i = f.lastIndexOf('.');   
  
        if (i > 0 &&  i < f.length() - 1) {   
            fname = f.substring(0,i);   
        }

        int lastIndex = fname.lastIndexOf(File.separator);
        if(lastIndex>0 && lastIndex<fname.length()-1){
        	fname = fname.substring(lastIndex-1);
        }
        
        return fname;   
    }
    
    /**
     * 读取源文件sFile，保存为目标文件tFile。
     * @param sFile
     * @param tFile
     * @return
     */
    public static boolean copyFile(String sFile, String tFile){
    	boolean success = true;
    	try
        {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File( sFile);
            if ( oldfile.exists() ) { //文件存在时
                InputStream inStream = new FileInputStream( sFile ); //读入原文件
                @SuppressWarnings("resource")
				FileOutputStream fs = new FileOutputStream( tFile );
                byte[] buffer = new byte[ 1444 ];
                int length;
                while ( ( byteread = inStream.read( buffer ) ) != -1 )
                {
                    bytesum += byteread; //字节数 文件大小
                    fs.write( buffer, 0, byteread );
                }
                inStream.close();
            }
        }
        catch ( Exception e )
        {
            System.out.println( "复制单个文件操作出错" );
            e.printStackTrace();
            success = false;

        }
    	return success;
    }
    
    /**
     * h
    * @Title: getPathOfResource 
    * @Description: 获取ip数据库的文件路径
    * @param @param file
    * @param @return    设定文件 
    * @return String    返回类型 
    * @throws
     */
    public static String getPathOfResource(String file){
		String resourceFile = null;
		try {
			resourceFile = System.getProperty("app.root");//web.xml定义的webAppRootKey
			resourceFile = resourceFile + "WEB-INF" + File.separatorChar + "config" +File.separatorChar + file ;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		return resourceFile;
    }
    


	public static void main(String[] args) throws IOException {
		// String result = getUrlAsString("http://www.gewara.com/");
		// System.out.println(result);
	}
}
