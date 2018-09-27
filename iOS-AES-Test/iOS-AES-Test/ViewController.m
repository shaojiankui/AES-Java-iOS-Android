//
//  ViewController.m
//  iOS-AES-Test
//
//  Created by Jakey on 2018/9/27.
//  Copyright © 2018 Jakey. All rights reserved.
//

#import "ViewController.h"
#import <CommonCrypto/CommonCryptor.h>

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    NSString *password = @"1234512345123456";
    NSString *str = @"12382929sadadasd";


    
    NSData *dataEncrypt  =  [self CCCryptData:[str dataUsingEncoding:NSUTF8StringEncoding]   operation:kCCEncrypt key:password];
    NSString *hex = [self convertDataToHexStr:dataEncrypt];
    NSLog(@"加密后:%@",hex);

    NSData *dataDecrypt  =  [self CCCryptData:[self convertHexStrToData:hex]   operation:kCCDecrypt key:password];
    NSString *decrypt = [[NSString alloc] initWithData:dataDecrypt encoding:NSUTF8StringEncoding];
    
    NSLog(@"解密后:%@",decrypt);

}

- (NSData *)CCCryptData:(NSData *)data
                 operation:(CCOperation)operation
                       key:(NSString *)key {
    NSMutableData *keyData = [[key dataUsingEncoding:NSUTF8StringEncoding] mutableCopy];
    
    size_t dataMoved;
    int size = kCCBlockSizeAES128;

     NSMutableData *decryptedData = [NSMutableData dataWithLength:data.length + size];
    
    int option = kCCOptionPKCS7Padding | kCCOptionECBMode;
  
 
    CCCryptorStatus result = CCCrypt(operation,                    // kCCEncrypt or kCCDecrypt
                                     kCCAlgorithmAES128,
                                     option,                        // Padding option for CBC Mode
                                     keyData.bytes,
                                     keyData.length,
                                     NULL,
                                     data.bytes,
                                     data.length,
                                     decryptedData.mutableBytes,    // encrypted data out
                                     decryptedData.length,
                                     &dataMoved);                   // total data moved
    
    if (result == kCCSuccess) {
        decryptedData.length = dataMoved;
        return decryptedData;
    }
    return nil;
}




- (NSData *)convertHexStrToData:(NSString *)str {
    if (!str || [str length] == 0) {
        return nil;
        
    }
    NSMutableData *hexData = [[NSMutableData alloc] initWithCapacity:8];
    NSRange range;
    if ([str length] % 2 == 0) {
        range = NSMakeRange(0, 2);
        
    } else {
        range = NSMakeRange(0, 1);
        
    }
    for (NSInteger i = range.location; i < [str length]; i += 2) {
        unsigned int anInt;
        NSString *hexCharStr = [str substringWithRange:range];
        NSScanner *scanner = [[NSScanner alloc] initWithString:hexCharStr];
        [scanner scanHexInt:&anInt];
        NSData *entity = [[NSData alloc] initWithBytes:&anInt length:1];
        [hexData appendData:entity];
        range.location += range.length;
        range.length = 2;
    }
    return hexData;
}

- (NSString *)convertDataToHexStr:(NSData *)data {
    if (!data || [data length] == 0) {
        return @"";
        
    }
    NSMutableString *string = [[NSMutableString alloc] initWithCapacity:[data length]];
    
    [data enumerateByteRangesUsingBlock:^(const void *bytes, NSRange byteRange, BOOL *stop) {
        unsigned char *dataBytes = (unsigned char*)bytes;
        for (NSInteger i = 0; i < byteRange.length; i++) {
            NSString *hexStr = [NSString stringWithFormat:@"%x", (dataBytes[i]) & 0xff]; if ([hexStr length] == 2) {
                [string appendString:hexStr];
            } else {
                [string appendFormat:@"0%@", hexStr];
            }
        }
    }];
    return string;
}

@end
