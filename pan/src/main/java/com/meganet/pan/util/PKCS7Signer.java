package com.meganet.pan.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaAlgorithmParametersConverter;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PKCS7Signer {

	@Autowired
	PropsValue propsValue;
	
    private static String KEY_ALIAS_IN_KEYSTORE;

    public PKCS7Signer() {
    }

    KeyStore loadKeyStore() throws Exception {

        KeyStore keystore = KeyStore.getInstance("JKS");
        InputStream is = new FileInputStream(propsValue.jksFilePath);
        keystore.load(is, propsValue.key.toCharArray());
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
		KEY_ALIAS_IN_KEYSTORE=alias;
        return keystore;
    }

    CMSSignedDataGenerator setUpProvider(final KeyStore keystore) throws Exception {

        Security.addProvider(new BouncyCastleProvider());

        Certificate[] certchain = (Certificate[]) keystore.getCertificateChain(KEY_ALIAS_IN_KEYSTORE);

        final List<Certificate> certlist = new ArrayList<Certificate>();

        for (int i = 0, length = certchain == null ? 0 : certchain.length; i < length; i++) {
            certlist.add(certchain[i]);
        }

        Store certstore = new JcaCertStore(certlist);

        Certificate cert = keystore.getCertificate(KEY_ALIAS_IN_KEYSTORE);
        PrivateKey priv =  (PrivateKey) (keystore.getKey(KEY_ALIAS_IN_KEYSTORE, propsValue.key.toCharArray()));
        ContentSigner signer = new JcaContentSignerBuilder("SHA1with"+priv.getAlgorithm()).setProvider("BC").
                build(priv);

        CMSSignedDataGenerator generator = new CMSSignedDataGenerator();

        generator.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(new JcaDigestCalculatorProviderBuilder().setProvider("BC").
                build()).build(signer, (X509Certificate) cert));

        generator.addCertificates(certstore);

        return generator;
    }

    byte[] signPkcs7(final byte[] content, final CMSSignedDataGenerator generator) throws Exception {

        CMSTypedData cmsdata = new CMSProcessableByteArray(content);
        CMSSignedData signeddata = generator.generate(cmsdata, true);
        return signeddata.getEncoded();
    }

    public byte[] signedData(String content) throws Exception {

        KeyStore keyStore = loadKeyStore();
        CMSSignedDataGenerator signatureGenerator = setUpProvider(keyStore);
//        String content = "some bytes to be signed";
        byte[] signedBytes = signPkcs7(content.getBytes("UTF-8"), signatureGenerator);
        //System.out.println("Signed Encoded Bytes: " + new String(Base64.encode(signedBytes)));
        return Base64.encode(signedBytes);
    }
}