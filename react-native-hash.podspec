require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-bytes"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-hash
                   DESC
  s.homepage     = "https://github.com/Drazail/react-native-bytes"
  s.license      = "MIT"
  # s.license    = { :type => "MIT", :file => "FILE_LICENSE" }
  s.authors      = { "Drazail" => "reza.y.3@email.com" }
  s.platforms    = { :ios => "9.0" }
  s.source       = { :git => "https://github.com/Drazail/react-native-bytes.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,m,swift}"
  s.requires_arc = true

  s.dependency "React"
  # ...
  # s.dependency "..."
end

