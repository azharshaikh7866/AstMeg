package com.meganet.pan.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import java.util.Enumeration;
import java.util.ArrayList;
import org.bouncycastle.util.encoders.Base64;
import java.security.cert.*;

public class pkcs7gen {
/*	public static byte[] signatureGeneration(String args,String filePath,String key) throws Exception {
	if(args.length < 3)
        {
            System.out.println("java pkcs7gen oupt.jks pfxpswd dataToBeSigned oupt.sig");
            System.exit(1);
        }


			KeyStore keystore = KeyStore.getInstance("jks");
//			File file = new File("resources/oupt.jks");
//			URL resource = pkcs7gen.class.getResource("oupt");
//			Paths.get(resource.toURI()).toFile();
//			File file = new File(resource.toURI()).getAbsoluteFile();D:\nsdl\apache-tomcat-7.0.50 - aua\webapps\aua\WEB-INF\classes
//			InputStream input = new FileInputStream("D:\\nsdl\\oupt.jks");//path to jks E:\\NSDL\\trunk\\web\\root\\src\\main\\resources\\
//			InputStream input = new FileInputStream("E:\\NSDL\\trunk\\web\\root\\src\\main\\resources\\oupt.jks");filePath
			InputStream input = new FileInputStream(filePath);
//			InputStream input = new FileInputStream("D:\\nsdl\\apache-tomcat-7.0.50 - aua\\webapps\\aua\\WEB-INF\\classes\\oupt.jks");
			System.out.println(input.toString());
			try {
				char[] password=key.toCharArray();//RCF - c0mpany  & RHF - Ind1an
				keystore.load(input, password);
			} catch (IOException e) {
			} finally {

			}
			Enumeration e = keystore.aliases();
			String alias = "";

			if(e!=null)
			{
				while (e.hasMoreElements())
				{
					String  n = (String)e.nextElement();
					if (keystore.isKeyEntry(n))
					{
						alias = n;
					}
				}
			}
			PrivateKey privateKey=(PrivateKey) keystore.getKey(alias, key.toCharArray());//RCF - c0mpany  & RHF - Ind1an
			X509Certificate myPubCert=(X509Certificate) keystore.getCertificate(alias);
			byte[] dataToSign=args.getBytes();
			CMSSignedDataGenerator sgen = new CMSSignedDataGenerator();
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider ());
			sgen.addSigner(privateKey, myPubCert,CMSSignedDataGenerator.DIGEST_SHA1);
			Certificate[] certChain =keystore.getCertificateChain(alias);
			ArrayList certList = new ArrayList();
			CertStore certs = null;
			for (int i=0; i < certChain.length; i++)
				certList.add(certChain[i]); 
			sgen.addCertificatesAndCRLs(CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC"));
			CMSSignedData csd = sgen.generate(new CMSProcessableByteArray(dataToSign),true, "BC");
			byte[] signedData = csd.getEncoded();
			byte[] signedData64 = Base64.encode(signedData); 
//			FileOutputStream out = new FileOutputStream(args[3]);
//			out.write(signedData64);
//			out.close(); 
//			System.out.println("Signature file written to "+args[3]);
			return signedData64;
		}*/

	
	}

