
  Pod::Spec.new do |spec|
    spec.name = 'NoDapAppleSignIn'
    spec.version = '0.1.1'
    spec.summary = 'Capacitor Apple Sign in'
    spec.license = 'MIT'
    spec.homepage = 'https://github.com/no-dap/apple-sign-in'
    spec.author = 'no-dap <somniholic@naver.com>'
    spec.source = { :git => 'https://github.com/no-dap/apple-sign-in.git', :tag => spec.version.to_s }
    spec.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    spec.ios.deployment_target  = '11.0'
    spec.dependency 'Capacitor'
    spec.swift_version = '5.3'
  end
