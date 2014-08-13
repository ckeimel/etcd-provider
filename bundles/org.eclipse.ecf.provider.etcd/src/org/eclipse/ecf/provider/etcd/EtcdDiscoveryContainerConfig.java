/*******************************************************************************
 * Copyright (c) 2014 Composent, Inc. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Scott Lewis - initial API and implementation
 ******************************************************************************/
package org.eclipse.ecf.provider.etcd;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.ecf.core.identity.IDFactory;
import org.eclipse.ecf.discovery.DiscoveryContainerConfig;
import org.eclipse.ecf.discovery.identity.IServiceTypeID;
import org.eclipse.ecf.discovery.identity.ServiceIDFactory;
import org.eclipse.ecf.provider.etcd.identity.EtcdNamespace;
import org.eclipse.ecf.provider.etcd.identity.EtcdServiceID;

public class EtcdDiscoveryContainerConfig extends DiscoveryContainerConfig {

	public static final String ETCD_TARGETID_PROTOCOL_PROP = EtcdDiscoveryContainerInstantiator.NAME
			+ ".protocol"; //$NON-NLS-1$
	public static final String ETCD_TARGETID_PROTOCOL_DEFAULT = System
			.getProperty(ETCD_TARGETID_PROTOCOL_PROP, "http"); //$NON-NLS-1$
	public static final String ETCD_TARGETID_HOSTNAME_PROP = EtcdDiscoveryContainerInstantiator.NAME
			+ ".hostname"; //$NON-NLS-1$
	public static final String ETCD_TARGETID_HOSTNAME_DEFAULT = System
			.getProperty(ETCD_TARGETID_HOSTNAME_PROP, "127.0.0.1"); //$NON-NLS-1$
	public static final String ETCD_TARGETID_PORT_PROP = EtcdDiscoveryContainerInstantiator.NAME
			+ ".port"; //$NON-NLS-1$
	public static final Integer ETCD_TARGETID_PORT_DEFAULT = Integer
			.getInteger(ETCD_TARGETID_PORT_PROP, 4001);
	public static final String ETCD_TARGETID_PATH_PROP = EtcdDiscoveryContainerInstantiator.NAME
			+ ".path"; //$NON-NLS-1$
	public static final String ETCD_TARGETID_PATH_DEFAULT = System.getProperty(
			ETCD_TARGETID_PATH_PROP, "/v2/keys"); //$NON-NLS-1$

	public static final String ETCD_TARGETID_PROP = EtcdDiscoveryContainerInstantiator.NAME
			+ ".targetid"; //$NON-NLS-1$
	public static final String ETCD_TARGETID_DEFAULT = System
			.getProperty(ETCD_TARGETID_PROP);

	private EtcdServiceID targetID;

	public EtcdDiscoveryContainerConfig(String containerId)
			throws MalformedURLException, URISyntaxException {
		super(IDFactory.getDefault().createStringID(containerId));
		setTargetID(null);
	}

	public EtcdDiscoveryContainerConfig(String containerId, String targetId)
			throws MalformedURLException, URISyntaxException {
		this(containerId);
		setTargetID(targetId);
	}

	private void setTargetID(String aTargetId) throws MalformedURLException,
			URISyntaxException {
		URL url = null;
		if (aTargetId == null)
			url = (ETCD_TARGETID_DEFAULT != null) ? new URL(
					ETCD_TARGETID_DEFAULT) : new URL(
					ETCD_TARGETID_PROTOCOL_DEFAULT,
					ETCD_TARGETID_HOSTNAME_DEFAULT,
					ETCD_TARGETID_PORT_DEFAULT.intValue(),
					ETCD_TARGETID_PATH_DEFAULT);
		else
			url = new URL(aTargetId);
		IServiceTypeID serviceTypeID = ServiceIDFactory.getDefault()
				.createServiceTypeID(EtcdNamespace.INSTANCE,
						EtcdNamespace.SCHEME);
		this.targetID = (EtcdServiceID) IDFactory.getDefault()
				.createID(EtcdNamespace.NAME,
						new Object[] { serviceTypeID, url.toURI() });
	}

	public EtcdServiceID getTargetID() {
		return targetID;
	}

}