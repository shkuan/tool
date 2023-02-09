package com.skuan.common;

import java.util.Locale;
import java.util.StringTokenizer;

public class SystemInfo {
    
    // platforms
    public static final boolean isLinux;
    public static final boolean isWindows;
    public static final boolean isMacOS;

    // OS architecture
	/** @since 2 */ public static final boolean isX86;
	/** @since 1.1 */ public static final boolean isX86_64;
	/** @since 2 */ public static final boolean isAARCH64;

    // Java versions
	public static final long javaVersion;
	public static final boolean isJava_9_orLater;
	public static final boolean isJava_11_orLater;
	public static final boolean isJava_15_orLater;
	/** @since 2 */ public static final boolean isJava_17_orLater;
	/** @since 2 */ public static final boolean isJava_18_orLater;

	// Java VMs
	public static final boolean isJetBrainsJVM;
	public static final boolean isJetBrainsJVM_11_orLater;

    static {
		// platforms
		String osName = System.getProperty( "os.name" ).toLowerCase( Locale.ENGLISH );
		isWindows = osName.startsWith( "windows" );
		isMacOS = osName.startsWith( "mac" );
		isLinux = osName.startsWith( "linux" );

        // OS architecture
		String osArch = System.getProperty( "os.arch" );
		isX86 = osArch.equals( "x86" );
		isX86_64 = osArch.equals( "amd64" ) || osArch.equals( "x86_64" );
		isAARCH64 = osArch.equals( "aarch64" );

        // Java versions
		javaVersion = scanVersion( System.getProperty( "java.version" ) );
		isJava_9_orLater = (javaVersion >= toVersion( 9, 0, 0, 0 ));
		isJava_11_orLater = (javaVersion >= toVersion( 11, 0, 0, 0 ));
		isJava_15_orLater = (javaVersion >= toVersion( 15, 0, 0, 0 ));
		isJava_17_orLater = (javaVersion >= toVersion( 17, 0, 0, 0 ));
		isJava_18_orLater = (javaVersion >= toVersion( 18, 0, 0, 0 ));

		// Java VMs
		isJetBrainsJVM = System.getProperty( "java.vm.vendor", "Unknown" ).toLowerCase( Locale.ENGLISH ).contains( "jetbrains" );
		isJetBrainsJVM_11_orLater = isJetBrainsJVM && isJava_11_orLater;
    }

    public static long scanVersion( String version ) {
		int major = 1;
		int minor = 0;
		int micro = 0;
		int patch = 0;
		try {
			StringTokenizer st = new StringTokenizer( version, "._-+" );
			major = Integer.parseInt( st.nextToken() );
			minor = Integer.parseInt( st.nextToken() );
			micro = Integer.parseInt( st.nextToken() );
			patch = Integer.parseInt( st.nextToken() );
		} catch( Exception ex ) {
			// ignore
		}

		return toVersion( major, minor, micro, patch );
	}

	public static long toVersion( int major, int minor, int micro, int patch ) {
		return ((long) major << 48) + ((long) minor << 32) + ((long) micro << 16) + patch;
	}

    
}
